package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.ItemDto;
import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.entity.BidEntity;
import com.ted.eBayDIT.entity.ItemEntity;
import com.ted.eBayDIT.entity.UserEntity;
import com.ted.eBayDIT.repository.ItemRepository;
import com.ted.eBayDIT.repository.UserRepository;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.ItemService;
import com.ted.eBayDIT.service.RecommendService;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.utility.Pair;
import com.ted.eBayDIT.utility.Utils;
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
    UserRepository userRepo;

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    private int stages=4;

    private int nearestUsersNum = 2;

    private Map<String, ArrayList<Double>> userVectorsHT  = new HashMap<String, ArrayList<Double>>();

    private List<ItemEntity> items;

    private LSHSuperBit lsh ;

    private List<Map<Integer, ArrayList<String>>> listOfMaps ;

//    private Map<Integer, ArrayList<String>> indexInLSH_usersHT = new HashMap<Integer, ArrayList<String>>();


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
                //todo tote des history of visits an ===0 des history visits

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

    private void initListOfMapsFromLSH(){

        if (this.listOfMaps == null) {
            this.listOfMaps = new ArrayList<Map<Integer, ArrayList<String>>>();

            for (int i = 0; i < this.stages; i++) {
                Map<Integer, ArrayList<String>> indexInLSH_usersHT = new HashMap<>();
                this.listOfMaps.add(indexInLSH_usersHT);
            }
        }

        /*for every userVector*/
        for ( String key : this.userVectorsHT.keySet() ) {
            System.out.println( key );

            if (isVectorZero(this.userVectorsHT.get(key))) //discard zero vectors!
                continue;


            double[] vector2insert = toPrimitive(userVectorsHT.get(key));

            int[] hashVector =  lsh.hash(vector2insert);
            //======================================== todo CHANGE BELOW

            /*for every lsh-HT */
            int listIndex=0;
            for (int indexInLSH : hashVector) {


                ArrayList<String> usernamesBucket = this.listOfMaps.get(listIndex).get(indexInLSH);

                if (usernamesBucket == null)
                    usernamesBucket = new ArrayList<>();

                usernamesBucket.add(key);
                this.listOfMaps.get(listIndex).put(indexInLSH,usernamesBucket);


                listIndex++;
            }


        }

    }


    @PostConstruct
    private void initLSH() {


        this.items = this.itemRepo.findByEventStartedTrue();
        int sizeOfVectors = items.size(); //aka number of auctions
        int numberOfBuckets = 10;
//        this.stages = 4;

        this.lsh = new LSHSuperBit(stages, numberOfBuckets, sizeOfVectors);


        init_userVectorsHT(items);

        initListOfMapsFromLSH();


    }

    private void recreateMaps(){
        this.userVectorsHT.clear();

        for (Map currMap : listOfMaps) {
            currMap.clear();
        }
        listOfMaps.clear();
        this.items.clear();

        this.items = this.itemRepo.findByEventStartedTrue();
        int sizeOfVectors = items.size(); //aka number of auctions

        init_userVectorsHT(items);

        initListOfMapsFromLSH();


    }



    //TODO GENIKA EDW PREPEI NA KANW FTIAKSW TO USERVECTOR TOU CURRENT USER KAI META NA TO HASHARW STO LSH
    //TODO META NA PARW TO BUCKET STO OPOIO PEFTEI
    //TODO APO KEI NA PARW TOUS ALLOUS USERS TOU BUCKET
    //TODO NA ELEGKW MESW USERNAME OTI DEN PHRA TON IDIO
    //TODO NA KANW COSINE SIMILARITY ME TON CURRENT USER ME TOUS ALLOUS TOU BUCKET
    //TODO NA PARW TOUS 10 KONTINOTEROUS (IF EXIST)
    //TODO OPOTE META NA TA SUNATHROISW KAI NA PARW TA 10 MEGALUTERA SCORE AUCTION
    //TODO KAI NA EPISTREPSW AUTA TA 10 AUCTIONS

    private int getIndexItem(ItemEntity item){
        int index=0;
        for (ItemEntity itemEntity : this.items) {
            if (item.getItemID().equals(itemEntity.getItemID()))
                return index;

            index++;
        }

        return -1;
    }
    @Override
    public List<ItemDto> getRecommendedAuctions() {
        List<ItemDto> returnValue= new ArrayList<>();
        UserEntity currUserEntity  = this.userRepo.findByUserId(  this.securityService.getCurrentUser().getUserId() ) ;

        //==================================================================================
        //todo ti tha ginei ama o arithmos twn auction exei megalwsei h mikrunei!
        List<ItemEntity> currentItems = this.itemRepo.findByEventStartedTrue();
        if ( this.items.equals(currentItems) ){ //if new items instance is different from current
            recreateMaps(); //recreate hash maps and hash to lsh
        }
        //==================================================================================

        //TODO CHECH IF USER ALREADY EXIST IN userVectorsHT

        ArrayList<Double> userVector2query = new ArrayList<Double>(Collections.nCopies(this.items.size(), 0.0)); //init userVector to query with zeros


        if ( currUserEntity.getBidder().getBids().size() == 0 ){

            //todo tote des history of visits
        }
        else {

            int index;
            for (BidEntity bid : currUserEntity.getBidder().getBids()) {

//                index = this.items.indexOf(bid.getItemDetails());
                index = getIndexItem(bid.getItemDetails());

                Double currently = bid.getItemDetails().getCurrently().doubleValue();
                Double amount = bid.getAmount().doubleValue();

                Double score = amount / currently;

                userVector2query.set(index,score);

            }
        }

        //====================================================
        //now query the userVector to lsh

        int[] hashVector =  lsh.hash( toPrimitive(userVector2query) );

        Set<String> allRelevantUsersSet = new HashSet<String>();



//        List<String> allRelevantUsersList = new ArrayList<>();

        int hashIndex;
        for (int i = 0; i < hashVector.length; i++) {
            hashIndex = hashVector[i];
            allRelevantUsersSet.addAll( listOfMaps.get(i).get(hashIndex) );

        }


        ArrayList<String> allRelevantUsersList = new ArrayList<>(allRelevantUsersSet);

//        List<String> relevantUsers = this.indexInLSH_usersHT.get(indexInLSH);

        List<Double> similarityScoreList =  new ArrayList<>();

            for (String relevantUser : allRelevantUsersSet) {


                System.out.println("here we do now the cosine similarity nad pick the best 10 users");


                ArrayList<Double> otherUserVector = this.userVectorsHT.get(relevantUser);

                Double similarityScore = Utils.euclideanDistance(toPrimitive(userVector2query), toPrimitive(otherUserVector));

                similarityScoreList.add(similarityScore);


            }

            //================================================
            //here sort both relevantUsers and similarityScoreLit and take the 5 most similar users




            ArrayList<Pair> pairList = new ArrayList<Pair>();

            for (int i = 0; i < allRelevantUsersSet.size(); i++) {

                if (allRelevantUsersList.get(i).equals(currUserEntity.getUsername())) //if the relevant username equals with current user continue
                    continue;

                pairList.add(new Pair(allRelevantUsersList.get(i), similarityScoreList.get(i)));

            }

            Collections.sort(pairList);



            //==================================================
            //todo now take the first 3 most similar user do the sum and take the top 5 auctions with the best score!!!

        ArrayList<Double> sumOfMostRelevantUserVectors = new ArrayList<>();

        for (int i = 0; i < this.nearestUsersNum; i++) {
            String nearestUserName = pairList.get(i).getE1();
            ArrayList<Double> nearestUserVector = this.userVectorsHT.get(nearestUserName);

            //TODO OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOODO EDW EIMAI AURIO APO EDW KSEKINAW!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//            sumOfMostRelevantUserVectors = Utils.sum2ArrayLists(toPrimitive(sumOfMostRelevantUserVectors) , toPrimitive(nearestUserVector) );

        }

        //todo now take the best 5 scores from there take the auctions



        //todo check the size if zero



        return returnValue;
    }






    private void calculateBest(){

    }


    @Override
    public void createLsh() {

        System.out.println("TESTING");


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
