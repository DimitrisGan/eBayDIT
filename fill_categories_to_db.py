from xml.etree import ElementTree
import mysql.connector


def recordAlreadyExists(tableName,whereClause,value):

	sql_find = "SELECT * FROM "+ str(tableName)+ " WHERE "+ str(whereClause)+" = %s"
	sql_tuple = (value, )
	cur.execute(sql_find, sql_tuple)
	returnValue = cur.fetchone()
	if returnValue is None:
		return False
	else:
		return True



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
for x in range(0,39):

	file_name = 'ebay-data/items-' + str(x) + '.xml'
	print '\nfile_name = '+file_name
	print '\n'
	tree = ElementTree.parse(file_name)

	
	items = tree.getroot()


	for item in items.findall('Item'):
		item_id = item.attrib['ItemID']

		name = item.find('Name')#.text

		category_list = item.findall('Category')

		for category in category_list:

			#insert category if not exist

			if not recordAlreadyExists('category','name', category.text):
				print 'NEW CATEGORY = '+ category.text

				#insert into bidder table
				query_categ = "INSERT INTO category(name) VALUES (%s)"
				sql_tuple_categ= (category.text,)
				cur.execute(query_categ,sql_tuple_categ)

