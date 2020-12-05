Use the following command to create a unique index on the email field:

![Source](https://docs.mongodb.com/manual/tutorial/unique-constraints-on-arbitrary-fields/)

    db.proxy.createIndex( { "email" : 1 }, { unique : true } )