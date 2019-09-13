package com.ted.eBayDIT.service.impl;


import com.ted.eBayDIT.dto.UserDto;
import com.ted.eBayDIT.entity.BidEntity;
import com.ted.eBayDIT.entity.ItemEntity;
import com.ted.eBayDIT.entity.UserEntity;
import com.ted.eBayDIT.entity.VisitEntity;
import com.ted.eBayDIT.repository.ItemRepository;
import com.ted.eBayDIT.repository.UserRepository;
import com.ted.eBayDIT.security.SecurityService;
import com.ted.eBayDIT.service.ItemService;
import com.ted.eBayDIT.service.RecommendService;
import com.ted.eBayDIT.service.UserService;
import com.ted.eBayDIT.service.VisitService;
import com.ted.eBayDIT.utility.Pair;
import com.ted.eBayDIT.utility.Pair2;
import com.ted.eBayDIT.utility.Utils;
import info.debatty.java.lsh.LSHSuperBit;
import org.modelmapper.ModelMapper;
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

    @Autowired
    VisitService visitService;


    private static final double visitScoreCoefficient = 0.05;

    private static final int stages=4;

    private static final int nearestUsersNum = 1;//3
    private static final int recommendAuctionsNum = 5;

    private Map<String, ArrayList<Double>> userVectorsHT  = new HashMap<String, ArrayList<Double>>();

    private List<ItemEntity> items;

    private LSHSuperBit lsh ;

    private List<Map<Integer, ArrayList<String>>> listOfMaps ;


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
                //todo see if should add in userVectors in lsh also the visits Score

                String username = bid.getBidder().getUser().getUsername();
                Double amount = bid.getAmount().doubleValue();
                Double currently = itemEntity.getCurrently().doubleValue();

                Double score = amount / currently;

                ArrayList<Double> userVector = userVectorsHT.get(username);
                userVector.set(index,score); //updates/replace also in hashMap the value

            }

            index++;
        }


    }

    private void initListOfMapsFromLSH(){

        if (this.listOfMaps == null) {
            this.listOfMaps = new ArrayList<Map<Integer, ArrayList<String>>>();
        }

        for (int i = 0; i < stages; i++) {
            Map<Integer, ArrayList<String>> indexInLSH_usersHT = new HashMap<>();
            this.listOfMaps.add(indexInLSH_usersHT);
        }

        /*for every userVector*/
        for ( String key : this.userVectorsHT.keySet() ) {

            if (isVectorZero(this.userVectorsHT.get(key))) //discard zero vectors!
                continue;

            double[] vector2insert = Utils.toPrimitive(userVectorsHT.get(key));

            int[] hashVector =  lsh.hash(vector2insert);

            /*for every lsh-HT */
            int listIndex=0;
            for (int indexInLSH : hashVector) {

                ArrayList<String> usernamesBucket = this.listOfMaps.get(listIndex).get(indexInLSH);

                if (usernamesBucket == null)
                    usernamesBucket = new ArrayList<>();

                usernamesBucket.add(key);
                this.listOfMaps.get(listIndex).put(indexInLSH,usernamesBucket); //maybe we can do it also with replace()

                listIndex++;
            }


        }

    }


    @PostConstruct
    private void initLSH() {

        this.items = this.itemRepo.findByEventStartedTrue();
        int sizeOfVectors = items.size(); //aka number of auctions
        int numberOfBuckets = 10;

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

        initLSH(); //recreate lsh instance
        
        System.out.println("Debug here");

    }


    private int getIndexItem(ItemEntity item){
        int index=0;
        for (ItemEntity itemEntity : this.items) {
            if (item.getItemID().equals(itemEntity.getItemID()))
                return index;

            index++;
        }

        return -1;
    }



    private ArrayList<Double> calculateUserVectorFromVisitsScore(UserEntity user){
        ArrayList<Double> userVector = new ArrayList<Double>(Collections.nCopies(this.items.size(), 0.0)); //init userVector to query with zeros

        int itemIndex;
        for (VisitEntity visitEntity : user.getItemsVisited()) {

            ItemEntity item =visitEntity.getItem();

            itemIndex = getIndexItem(item);

            double score = visitEntity.getVisitsTimes() * visitScoreCoefficient;
            if (score > 1.0) //pay attention to not surpass the maximum which is 1!
                score = 1.0;

            userVector.set(itemIndex,score);
        }


        return userVector;
    }

    private ArrayList<Double> calculateUserVectorFromBidsScore(UserEntity user) {
        ArrayList<Double> userVector = new ArrayList<Double>(Collections.nCopies(this.items.size(), 0.0)); //init userVector to query with zeros

        int itemIndex;
        for (BidEntity bid : user.getBidder().getBids()) {

            itemIndex = getIndexItem(bid.getItemDetails());

            Double currently = bid.getItemDetails().getCurrently().doubleValue();
            Double amount = bid.getAmount().doubleValue();

            Double score = amount / currently;

            userVector.set(itemIndex,score);

        }
        return userVector;

    }

    private ArrayList<Double> calculateSimilarityScoreBetweenUsers(ArrayList<Double> userVector2query , ArrayList<String> allRelevantUsersList) {

        ArrayList<Double> similarityScoreList =  new ArrayList<>();

        for (String relevantUser : allRelevantUsersList) {

            ArrayList<Double> otherUserVector = this.userVectorsHT.get(relevantUser);

            Double similarityScore = Utils.cosineDistance(Utils.toPrimitive(userVector2query), Utils.toPrimitive(otherUserVector));

            similarityScoreList.add(similarityScore);

        }

        return similarityScoreList;
    }


    private ArrayList<String> getAllRelevantUsersList(ArrayList<Double> userVector2query) {

        int[] hashVector =  lsh.hash( Utils.toPrimitive(userVector2query) );

        Set<String> allRelevantUsersSet = new HashSet<>();

        int hashIndex;
        for (int i = 0; i < hashVector.length; i++) { //merge all buckets from the lsh hash Maps to one
            hashIndex = hashVector[i];
            allRelevantUsersSet.addAll( listOfMaps.get(i).get(hashIndex) ); //do not add duplicates usernames
        }

        return new ArrayList<>(allRelevantUsersSet);

    }

    private ArrayList<Pair> getSortedUsersSimilarityTupleList(UserEntity currUserEntity, ArrayList<String> allRelevantUsersList,List<Double> similarityScoreList) {

        ArrayList<Pair> pairList = new ArrayList<Pair>();

        for (int i = 0; i < allRelevantUsersList.size(); i++) {

            if (allRelevantUsersList.get(i).equals(currUserEntity.getUsername())) //if the relevant username equals with current user continue
                continue;

            pairList.add(new Pair(allRelevantUsersList.get(i), similarityScoreList.get(i)));

        }

        Collections.sort(pairList);

        return pairList;
    }

    private ArrayList<Double> getSumVectorOfTheNmostSimilarUserVectors(ArrayList<Pair> pairList){
        ArrayList<Double> sumOfMostRelevantUserVectors = new ArrayList<Double>(Collections.nCopies(this.items.size(), 0.0)); //init userVector to query with zeros

        for (int i = 0; i < nearestUsersNum; i++) {
            String nearestUserName = pairList.get(i).getE1();
            ArrayList<Double> nearestUserVector = this.userVectorsHT.get(nearestUserName);

            sumOfMostRelevantUserVectors = Utils.sum2ArrayLists(Utils.toPrimitive(sumOfMostRelevantUserVectors) , Utils.toPrimitive(nearestUserVector) );

        }

        return sumOfMostRelevantUserVectors;
    }

    private ArrayList<Pair2> getSortedAuctionsScoreTupleList(ArrayList<Double> sumOfMostRelevantUserVectors){

        ArrayList<Pair2> pairList2 = new ArrayList<Pair2>();

        for (int i = 0; i < sumOfMostRelevantUserVectors.size(); i++) {
            pairList2.add(new Pair2(i, sumOfMostRelevantUserVectors.get(i)));
        }

        /* Sorting in decreasing (descending) order*/
        pairList2.sort(Collections.reverseOrder());

        return pairList2;
    }


    @Override
    public List<Long> getRecommendedAuctionIdsForUser() {
        List<Long> returnValue= new ArrayList<>();

        UserEntity currUserEntity  = this.userRepo.findByUserId(  this.securityService.getCurrentUser().getUserId() ) ;

        //==================================================================================
        List<ItemEntity> currentItems = this.itemRepo.findByEventStartedTrue();

//        if ( ! (this.items.containsAll(currentItems) && this.items.size() == currentItems.size() ) ) //if new items instance is different from current
         if (this.items.size() != currentItems.size())
            recreateMaps(); //recreate hash maps and hash to lsh

        //==================================================================================

        ArrayList<Double> userVector2query;

        if ( currUserEntity.getBidder().getBids().size() == 0 ) { //if true means that we have to check users visit history
            userVector2query = calculateUserVectorFromVisitsScore(currUserEntity); //get vector Score from visits history
        } else {
            userVector2query = calculateUserVectorFromBidsScore(currUserEntity); //get vector Score from bids history
        }

        //==================================================================================

        /*query the userVector to lsh and ge all relevant users from all the buckets of lsh hashMpas*/
        ArrayList<String> allRelevantUsersList = getAllRelevantUsersList(userVector2query);

        /*calculate distance/similarity our user with the other*/
        List<Double> similarityScoreList = calculateSimilarityScoreBetweenUsers(userVector2query,allRelevantUsersList);

        //==================================================================================

        /*here make a pair List and sort both relevantUsers and similarityScoreList and take the 5 most similar users*/
        ArrayList<Pair> pairList = getSortedUsersSimilarityTupleList(currUserEntity,allRelevantUsersList,similarityScoreList);


        //==================================================================================

        /*now take the first n most similar user do the sum and take the top k auctions with the best score!!!*/

        ArrayList<Double> sumOfMostRelevantUserVectors = getSumVectorOfTheNmostSimilarUserVectors(pairList); //init userVector to query with zeros


//        sumOfMostRelevantUserVectors

        ArrayList<Pair2> pairList2 = getSortedAuctionsScoreTupleList(sumOfMostRelevantUserVectors);


        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        /*now take the k best auction scores */
        int auctions2recommendNum = recommendAuctionsNum;
        for (int i = 0; i < auctions2recommendNum; i++) {

            int itemIndex =  pairList2.get(i).getIndex();

            ItemEntity itemEntity2recommend = this.items.get(itemIndex);

            if ( itemEntity2recommend.isEventFinished()) { //if auction has finished continue to the next one!Because we cant recommend a finished auction
                auctions2recommendNum++; //check to recommend 1 more  because we want to return 5 auctions recommendations
                continue;
            }

            returnValue.add(itemEntity2recommend.getItemID());
        }


        return returnValue;
    }

    @Override
    public List<Long> getRecommendedAuctionIdsForGuest() {
       return this.visitService.getMostVisitedAuctions(recommendAuctionsNum);
    }


    @Override
    public void createLsh() {

        System.out.println("TESTING");


    }




}
