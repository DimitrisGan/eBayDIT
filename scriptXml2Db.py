from xml.etree import ElementTree
import mysql.connector




def getLabelAttrib(parentClass ,label):
	bidder_location = bidder.find('Location')
	x = parentClass.find(label)
	if x is not None:		
		return x.text
	else:
		return "None"


def recordAlreadyExists(tableName,whereClause,value):

	sql_find = "SELECT * FROM "+ str(tableName)+ " WHERE "+ str(whereClause)+" = %s"
	print 'sql_find ~~~~~~~============> '+sql_find
	sql_tuple = (value, )

	cur.execute(sql_find, sql_tuple)

	returnValue = cur.fetchone()
	print 'returnValue--------> '+ str(returnValue)

	if returnValue is None:
		return False
	else:
		return True


def insertLocationItem(lat,long,txt):

	sql_find_loc_item = "SELECT * FROM location WHERE latitude = %s AND longitude = %s and text = %s"

	sql_loc_tuple = (lat,long,txt )

	cur.execute(sql_find_loc_item, sql_loc_tuple)

	returnValueLoc = cur.fetchone()#[0]

	if returnValueLoc is not None: # it means that the current loc_item already exist in table 
		pass
	else: # it means that the current loc_item doenst exist already 
		query_loc_item = "INSERT INTO location(latitude,longitude,text) VALUES (%s, %s, %s)"
		cur.execute(query_loc_item, sql_loc_tuple)

	

def getLocationItemId(lat,long,txt):
	sql_find_loc_item = "SELECT id FROM location WHERE latitude = %s AND longitude = %s and text = %s"
	sql_loc_tuple = (lat,long,txt )
	cur.execute(sql_find_loc_item, sql_loc_tuple)
	id = cur.fetchone()[0]

	return id


def getSellerId(seller_UserId_attr):
	sql_find_seller_id_by_userID = "SELECT id FROM seller WHERE user_username = %s"

	sql_tuple_UserID_seller = (seller_UserId_attr,)
	cur.execute(sql_find_seller_id_by_userID, sql_tuple_UserID_seller)
	print '========================================'
	seller_id = cur.fetchone()[0]#all()[0][0]
	return seller_id


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


tree = ElementTree.parse('ebay-data/items-5.xml')


items = tree.getroot()
print items, items.tag, items.attrib


for item in items.findall('Item'):
	item_id = item.attrib['ItemID']
	print "ItemID: "+str(item_id)

	name = item.find('Name')#.text
	print 'Name: '+name.text

	category_list = item.findall('Category')

	print "Categories:"
	for category in category_list:
		print '\t'+ category.text

		#insert category if not exist

		if not recordAlreadyExists('category','name', category.text):
			#insert into bidder table
			query_categ = "INSERT INTO category(name) VALUES (%s)"
			sql_tuple_categ= (category.text,)
			cur.execute(query_categ,sql_tuple_categ)


	buy_price = item.find('Buy_Price')
	if buy_price is None:
		buy_price="None"
	else:
		buy_price = buy_price.text

	print 'Buy_Price: '+ buy_price

	#-----------------------------------

	#insert Location!!!
	location_item = item.find('Location')

	location_text 	   = "None"
	location_lat_attr  = "None"
	location_long_attr = "None"
	dictExists = bool(location_item.attrib) #if dict attrbites exist then extract long-lat information

	if dictExists: # of type :  <Location Latitude="40.849879" Longitude="-73.97501">Fort Lee NJ</Location>

		location_lat_attr = location_item.attrib['Latitude']
		location_long_attr = location_item.attrib['Longitude']
		print '\t Location lat ---> '+ str(location_lat_attr)
		print '\t Location long ---> '+ str(location_long_attr)

		
	else: #of type <Location>Smackover, AR</Location>

		location_text = location_item.text		
		print 'Location-shop: '+ location_text

	insertLocationItem(location_lat_attr,location_long_attr,location_text)


	# sql_find_loc_item = "SELECT * FROM location WHERE latitude = %s AND longitude = %s and text = %s"

	# sql_loc_tuple = (location_lat_attr,location_long_attr,location_text )

	# cur.execute(sql_find_loc_item, sql_loc_tuple)

	# returnValueLoc = cur.fetchone()

	# if returnValueLoc is not None: # it means that the current loc_item already exist in table 
	# 	pass
	# else: # it means that the current loc_item doenst exist already 
	# 	query_loc_item = "INSERT INTO location(latitude,longitude,text) VALUES (%s, %s, %s)"
	# 	cur.execute(query_loc_item, sql_loc_tuple)


	# print "Location: "+str(item_id)



	#-----------------------------------

	currently = item.find('Currently')
	print 'Currently: '+ currently.text

	first_bid = item.find('First_Bid')
	print 'First_Bid: '+ first_bid.text
	
	number_of_bids = item.find('Number_of_Bids')
	print 'Number_of_Bids: '+number_of_bids.text
	
	#Bids!!

	location = item.find('Location')#.text
	print 'Location: '+location.text

	country_item = item.find('Country')
	print 'Country: '+country_item.text

	started = item.find('Started')
	print 'Started: '+started.text

	ends = item.find('Ends')
	print 'Ends: '+ends.text

	#Seller inside tag!!

	seller = item.find('Seller')
	seller_UserId_attr=str(seller.attrib['UserID'])
	seller_rating_attr=int(seller.attrib['Rating'])

	print "Seller-UserID-attr: "+str(seller_UserId_attr)
	print "Seller-Rating-attr: "+str(seller_rating_attr)



	#--------------------------------------

	if not recordAlreadyExists('seller','user_username', seller_UserId_attr):
		#insert into bidder table
		query_seller = "INSERT INTO seller(rating,user_username) VALUES (%s, %s)"
		sql_tuple_seller = (seller_rating_attr,seller_UserId_attr )
		cur.execute(query_seller,sql_tuple_seller)


	#--------------------------------------


	descr = item.find('Description')
	# if descr is None:	
	# 	print 'None'
	# else:
	print 'Description: '+str(descr.text)
	# print '\n'

	bids = item.find('Bids')
	for bid in bids.findall('Bid'):
		bidder = bid.find('Bidder')
		bidder_rating_attr = int(bidder.attrib['Rating'])
		print "\tBid-Bidder-Rating-attr: "+str(bidder_rating_attr)
		bidder_UserId_attr = str(bidder.attrib['UserID'])
		print "\tBid-Bidder-UserID-attr: "+str(bidder_UserId_attr)

		#bidder is in tag!
		# bidder_location = bidder.find('Location')
		# if bidder_location is not None:		
		# 	print '\t\tBid-Bidder-Location: '+str(bidder_location.text)
		bidder_location = getLabelAttrib(bidder,'Location')

		# bidder_country = bidder.find('Country')
		# print '\t\tBid-Bidder-Country: '+str(bidder_country.text)
		bidder_country = getLabelAttrib(bidder,'Country')

		bid_time = bid.find('Time')
		print '\tBid-Time: '+str(bid_time.text)

		bid_amount = bid.find('Amount')
		print '\tBid-Amount: '+str(bid_amount.text)


		if not recordAlreadyExists('bidder','user_username', bidder_UserId_attr):
			#insert into bidder table
			query_bidder = "INSERT INTO bidder(country,location,rating,user_username) VALUES (%s, %s, %s, %s)"
			sql_tuple_bidder= (	bidder_country,	bidder_location,	bidder_rating_attr,bidder_UserId_attr )
			cur.execute(query_bidder,sql_tuple_bidder)


		sql_find_bidder_by_userID = "SELECT id FROM bidder WHERE user_username = %s"
		sql_tuple_UserID_bidder = (bidder_UserId_attr, )
		cur.execute(sql_find_bidder_by_userID, sql_tuple_UserID_bidder)
		print '========================================'
		bidder_id = cur.fetchone()[0]#all()[0][0]

		
		query_bid = "INSERT INTO bid(time,amount,bidder_id,item_id) VALUES (%s, %s, %s, %s)"
		sql_tuple_bid= (bid_time.text ,bid_amount.text ,bidder_id, item_id)
		cur.execute(query_bid, sql_tuple_bid)
		# mydb.commit()
		print(cur.rowcount, "record inserted.")



	#find seller_id
	seller_id =getSellerId(seller_UserId_attr)

	#find location_id
	location_id = getLocationItemId(location_lat_attr,location_long_attr,location_text)

	#insert item
	query_item = "INSERT INTO item(item_id,buy_price,country,currently,description,ends,first_bid,name,number_of_bids,started,seller_id,location_id) VALUES (%s, %s, %s, %s,%s, %s, %s, %s,%s, %s, %s, %s)"
	sql_tuple_item= ( item_id, buy_price ,country_item.text , currently.text ,descr.text, ends.text, first_bid.text ,name.text , number_of_bids.text, started.text ,seller_id , location_id )

	cur.execute(query_item, sql_tuple_item)



		