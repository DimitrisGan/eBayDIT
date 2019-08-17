//package com.ted.eBayDIT;
//
//
//import com.ted.eBayDIT.xmlParser.Items;
//
//import javax.annotation.PostConstruct;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import javax.xml.bind.Unmarshaller;
//import java.io.File;
//
//public class populateDataToDb {
//
////    @PostConstruct
////    public void extractXml() {
////        try {
////            File file = new File("/home/dimitrisgan/Desktop/eBayD_v2/ebay-data/items-2.xml");
////            JAXBContext jaxbContext = JAXBContext.newInstance(Items.class);
////            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
////            Items allItems = (Items) jaxbUnmarshaller.unmarshal(file);
////            System.out.println(allItems);
////
////        } catch (JAXBException e) {
////            e.printStackTrace();
////        }
////    }
//
//
////    @PostConstruct
////    private void createXml() {
////
////
////        try {
////
////            Items allItems = new Items();
////
////            File output_file = new File("/home/dimitrisgan/Desktop/eBayD_v2/ebay-data/myOutput__items-2.xml");
////            JAXBContext jaxbContext = JAXBContext.newInstance(Items.class);
////            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
////            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
////
////            jaxbMarshaller.marshal(allItems, output_file);
////            jaxbMarshaller.marshal(allItems, System.out);
////
////
////        } catch (JAXBException e) {
////            e.printStackTrace();
////        }
////
////
////    }
//
//}
//
//
//
//
////todo initialize db properly
////    @PostConstruct
////    private void initDB() {
////        if (roleRepo.findByUserRole(RoleName.ADMIN.name()) == null) {
////            RoleEntity adminRole = new RoleEntity(RoleName.ADMIN.name());
////            roleRepo.save(adminRole);
////        }
////        if (roleRepo.findByUserRole(RoleName.USER.name()) == null) {
////            RoleEntity userRole = new RoleEntity(RoleName.USER.name());
////            roleRepo.save(userRole);
////        }
////        if (userRepo.findByEmail("dimgan@di.uoa.gr") == null) {
////            UserEntity admin1 = new UserEntity();
////            admin1.setUsername("Dim_gan");
////            admin1.setEncryptedPassword("admin1234");
////            admin1.setEmail("dimgan@di.uoa.gr");
////            admin1.setFirstName("Dimitris");
////            admin1.setLastName("Gangas");
////            admin1.setIsVerifiedByAdmin(true);
////
////            saveAdmin(admin1);
////
////        }
////        if (userRepo.findByEmail("ylam@di.uoa.gr") == null) {
////            UserEntity admin2 = new UserEntity();
////            admin2.setUsername("Yannis_Lam");
////            admin2.setEncryptedPassword("admin1234");
////            admin2.setEmail("ylam@di.uoa.gr");
////            admin2.setFirstName("Yannis");
////            admin2.setLastName("Lamprou");
////            admin2.setIsVerifiedByAdmin(true);
////
////            saveAdmin(admin2);
////
////        }
////
////
////
////    }
////
////
////    private void saveAdmin(UserEntity user) {
////        user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getEncryptedPassword()));
////        user.setRole(roleRepo.findByUserRole(RoleName.ADMIN.name()));
////        //todo put exception if user is null
////        userRepo.save(user);
////    }
