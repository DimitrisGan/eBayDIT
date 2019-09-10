package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.entity.BidEntity;
import com.ted.eBayDIT.entity.ItemEntity;
import com.ted.eBayDIT.repository.ItemRepository;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.ItemService;
import com.ted.eBayDIT.service.RecommendService;
import com.ted.eBayDIT.service.UserService;
import info.debatty.java.lsh.LSHSuperBit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;



    private Map<String, ArrayList<Double>> userVectorsHT  = new HashMap<String, ArrayList<Double>>();

    private LSHSuperBit lsh ;

    private Map<Integer, ArrayList<String>> indexInLSH_usersHT = new HashMap<Integer, ArrayList<String>>();


    private boolean isVectorZero(ArrayList<Double> vector){
        for (Double value : vector) {
            if (value != 0.0)
                return false;
        }
        return true;
    }




    private void init_userVectorsHT(List<ItemEntity> items){
        int sizeOfVectors = items.size(); //aka number of auctions

        for (UserDto verifiedUser : userService.getAllVerifiedUsers()) {

            ArrayList<Double> zeroVector = new ArrayList<Double>(Collections.nCopies(sizeOfVectors, 0.0));
            this.userVectorsHT.put(verifiedUser.getUsername(),zeroVector);
        }


        int index=0;
        for (ItemEntity itemEntity : items) {

            for (BidEntity bid : itemEntity.getBids()) {

                String username = bid.getBidder().getUser().getUsername();
                Double amount = bid.getAmount().doubleValue();
                Double currently = itemEntity.getCurrently().doubleValue();

                Double score = amount / currently;

                ArrayList<Double> userVector = userVectorsHT.get(username);
                userVector.set(index,score);
//                this.userVectorsHT.replace(username,userVector);    //PERITTO gt einai byreference update the userHashMap
            }

            index++;
        }


    }

    private void init_indexInLSH_usersHT(){

        for ( String key : this.userVectorsHT.keySet() ) {
            System.out.println( key );

            if (isVectorZero(this.userVectorsHT.get(key))) //discard zero vectors!
                continue;


            double[] vector2insert = toPrimitive(userVectorsHT.get(key));

            int[] hashVector =  lsh.hash(vector2insert);

            int indexInLSH = hashVector[hashVector.length-1];

            ArrayList<String> usernames = this.indexInLSH_usersHT.get(indexInLSH);
            if (usernames == null)
                usernames = new ArrayList<>();

            usernames.add(key);

            indexInLSH_usersHT.put(indexInLSH,usernames);

        }

    }


    @PostConstruct
    private void initLSH() {


        List<ItemEntity> items = itemRepo.findAll();
        int sizeOfVectors = items.size(); //aka number of auctions
        int numberOfBuckets = 10;
        int stages = 4;

        this.lsh = new LSHSuperBit(stages, numberOfBuckets, sizeOfVectors);


        init_userVectorsHT(items);

        init_indexInLSH_usersHT();


    }




    @Override
    public List<ItemDto> getRecommendedAuctions() {

        this.securityService.getCurrentUser().getUsername();



        return null;
    }




    @Override
    public void createLsh() {

        //todo create vector for each user isws se hash table
//        initializeUserVectorsHT();
        System.out.println("TESTING");
        System.out.println(this.indexInLSH_usersHT.size());

    }



    private static double[] toPrimitive(ArrayList<Double> arrayL) {
        if (arrayL == null) {
            return null;
        }
        final double[] result = new double[arrayL.size()];
        for (int i = 0; i < arrayL.size(); i++) {
            result[i] = arrayL.get(i);
        }

        return result;
    }

}
