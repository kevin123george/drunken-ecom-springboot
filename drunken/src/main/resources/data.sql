--populate bottle
insert into bottle (bottle_pic, in_stock, is_alcoholic, name, price, supplier, volume, volume_percent) values ('https://images.squarespace-cdn.com/content/v1/55c8073fe4b02a74ffe18e48/1628558313685-UDP6RRAUJGGWPDUY2AFM/ddyzjjz-2cf52bdc-1857-4fe9-a9cb-df8dc21e2001.jpg', '45',true,'testbottle1',12,'Astra',1000,45)
insert into bottle (bottle_pic, in_stock, is_alcoholic, name, price, supplier, volume, volume_percent) values ('https://images.squarespace-cdn.com/content/v1/55c8073fe4b02a74ffe18e48/1628558331565-777EQ5IHSVJ1GQKCSIDV/ddy1neo-dedb660f-4ca1-49ea-b04a-704efcd55c28.jpg', '8',false,'testbottle2',2,'Astra',1000,5)

--populate crate
insert into crate (crate_in_stock, crate_pic, name, no_of_bottles, price) values (89, 'https://images.squarespace-cdn.com/content/v1/55c8073fe4b02a74ffe18e48/1628558331337-RE95FIEDLQFOA0ZNZOGK/ddvz54w-1c415202-7e48-4ee6-a51e-9c75904b4a52.jpg', 'testcrate1', 4, 18)
insert into crate (crate_in_stock, crate_pic, name, no_of_bottles, price) values (9, 'https://images.squarespace-cdn.com/content/v1/55c8073fe4b02a74ffe18e48/1628558326233-2WK0FB9PAN7V3LRLW0RS/de8ug2n-ee79821f-751c-4a58-aa12-be8ff232185d.jpg', 'testcrate2', 4, 8)