# The Drunken

an ecommerce website to sell liqures 

## How to
To run the application first we need to set up our database. Personally, I prefer Postgres but we can also opt for H2

In application properties, uncomment the Postgres configuration to use Postgres for H2. Comment on Postgres configuration and uncomment H2


Please note we need to create Postgres DB if you are using Postgres and on the initial run spring.jpa.hibernate.ddl-auto should be create
default port : 8080

##  Db Desing

![db Design](db.png?raw=true "Db Design")

## Pages
1. login (have validations)
2. registeration (have validations)
3. index (can filter and search product, list all products)
4. add-crate (have validations)
5. add-bottle (have validations)
6. Profile (see order history and edit address)
7. edit/add address (have validations)
8. order_summery (support basic operation like checkout delete order line)
9. product_page (product details, add to cart)



## Good to have
may be write test cases 

https://gitlab.rz.uni-bamberg.de/dsg/dsam/group7/-/releases/latest-final
