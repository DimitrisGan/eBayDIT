from xml.etree import ElementTree
import mysql.connector


categories_counter=0

def recordAlreadyExists(tableName,whereClause,value):

    sql_find = "SELECT * FROM "+ str(tableName)+ " WHERE "+ str(whereClause)+" = %s"
    sql_tuple = (value, )
    cur.execute(sql_find, sql_tuple)
    returnValue = cur.fetchone()
    if returnValue is None:
        return False
    else:
        return True

def getParentId(value):
    sql_find = "SELECT id FROM category WHERE name = %s"
    sql_tuple = (value, )
    cur.execute(sql_find, sql_tuple)
    parent_id = cur.fetchone()[0]

    return parent_id

# db connection code is unchanged
db = mysql.connector.Connect(host = 'localhost', user = 'root', password ='password' , database = 'db_v2')


# Check if connection was successful
if (db):
    # Carry out normal procedure
    print "Connection successful"
else:
    # Terminate
    print "Connection unsuccessful"

cur = db.cursor()
for x in range(0,4):

    file_name = 'ebay-data/items-' + str(x) + '.xml'
    print '\nfile_name = '+file_name + '\n'

    tree = ElementTree.parse(file_name)

    items = tree.getroot()
    parent_name = "None"

    for item in items.findall('Item'):
        item_id = item.attrib['ItemID']

        category_list = item.findall('Category')


        for level,category in enumerate(category_list):

            #insert category if not exist already in db
            if not recordAlreadyExists('category','name', category.text):
                if level == 0: #is root so give -1 id
                    parent_id = -1
                else:
                    parent_id = getParentId(parent_name)

                    print 'New category:: Name: {} ,level: {} ,parent: {} ,parent_id: {}'.format(category.text,level,parent_name,parent_id)

                categories_counter+=1
                query_categ = "INSERT INTO category(name,level,parent_id) VALUES (%s,%s,%s)"
                sql_tuple_categ= (category.text,level,parent_id)
                cur.execute(query_categ,sql_tuple_categ)
                db.commit()

            parent_name = category.text # make category parent

print 'Number of Categories added: '+str(categories_counter)
