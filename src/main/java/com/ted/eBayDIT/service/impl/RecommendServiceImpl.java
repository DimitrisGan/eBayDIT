package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.BidDto;
import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.entity.BidEntity;
import com.ted.eBayDIT.entity.ItemEntity;
import com.ted.eBayDIT.repository.ItemRepository;
import com.ted.eBayDIT.service.ItemService;
import com.ted.eBayDIT.service.RecommendService;
import com.ted.eBayDIT.service.UserService;
import info.debatty.java.lsh.LSHMinHash;
import info.debatty.java.lsh.LSHSuperBit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

@Service
public class RecommendServiceImpl implements RecommendService {


    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    ItemService itemService;


    @Autowired
    UserService userService;


    private Map<String, ArrayList<Double>> userVectorsHT  = new HashMap<String, ArrayList<Double>>();


    private LSHSuperBit lsh ;
    //todo edw tha xwsw to lsh hash table type logika


    private Map<Integer, ArrayList<String>> indexInLSH_usersHT = new HashMap<Integer, ArrayList<String>>();

    private boolean isVectorZero(ArrayList<Double> vector){
        for (Double value : vector) {
            if (value != 0.0)
                return false;
        }
        return true;
    }


    //initialize userVectors and lsh
    @PostConstruct
    private void initializeUserVectorsHT() {


        List<ItemEntity> items = itemRepo.findAll();
        int sizeOfVectors = items.size(); //aka number of auctions
        int numberOfBuckets = 10;
        int stages = 4;

        this.lsh = new LSHSuperBit(stages, numberOfBuckets, sizeOfVectors);

//        double[] vector1 = new double[] {1, 2, 3, 4, 5};

//        int[] firstHash = lsh.hash(vector1);


//        ArrayList<Double> zeroVector= new ArrayList<>(sizeOfVectors);

//        ArrayList<Double> zeroVector= new ArrayList<>(Arrays.asList(new Double[sizeOfVectors]));
//        Collections.fill(zeroVector, 0.0);//fills all 40 entries with 0

        for (UserDto verifiedUser : userService.getAllVerifiedUsers()) {
//            List<Double> zeroVector = Collections.nCopies(sizeOfVectors, 0.0);
            ArrayList<Double> zeroVector = new ArrayList<Double>(Collections.nCopies(sizeOfVectors, 0.0));
            this.userVectorsHT.put(verifiedUser.getUsername(),zeroVector);
        }

        //todo ana bidder na pairnw ton user

        int index=0;
        for (ItemEntity itemEntity : items) {
//            itemDto.getBids().get(0).getBidder().getUser().getUsername();

            for (BidEntity bid : itemEntity.getBids()) {
                String username = bid.getBidder().getUser().getUsername();
                Double amount = bid.getAmount().doubleValue();
                Double currently = itemEntity.getCurrently().doubleValue();

                Double score = amount / currently;

                ArrayList<Double> userVector = userVectorsHT.get(username);
                userVector.set(index,score);
                this.userVectorsHT.replace(username,userVector);    //PERITTO gt einai byreference update the userHashMap
            }

            index++;
        }


        for ( String key : this.userVectorsHT.keySet() ) {
            System.out.println( key );

            if (isVectorZero(this.userVectorsHT.get(key)))
                continue;


            double[] vector2insert = toPrimitive(userVectorsHT.get(key));

            int[] hashVector =  lsh.hash(vector2insert);

            int indexInLSH = hashVector[hashVector.length-1];

            ArrayList<String> usernames = this.indexInLSH_usersHT.get(indexInLSH);
            if (usernames == null)
                usernames = new ArrayList<>();

            usernames.add(key);

            indexInLSH_usersHT.put(indexInLSH,usernames);


            System.out.println(hashVector);

        }


        //todo isws na kanw discard ta mhdenika vectors ///isws kai oxi

        System.out.println("telos edw");
    }


    @Override
    public void createLsh() {

        //todo create vector for each user isws se hash table
//        initializeUserVectorsHT();
        System.out.println("TESTING");
        System.out.println(this.indexInLSH_usersHT.size());

    }




    @Override
    public List<ItemDto> getRecommendedAuctions() {
        return null;
    }



    private static double[] toPrimitive(ArrayList<Double> arrayL) {
        if (arrayL == null) {
            return null;
        }
        final double[] result = new double[arrayL.size()];
        for (int i = 0; i < arrayL.size(); i++) {
            result[i] = arrayL.get(i).doubleValue();
        }

        return result;
    }

}
