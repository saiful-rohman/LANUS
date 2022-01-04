package com.javaindo.lautnusantara.repository

import com.javaindo.lautnusantara.model.PortMapModel
import com.javaindo.lautnusantara.model.SearchSellingPriceParmModel
import com.javaindo.lautnusantara.model.SellPriceModel
import com.javaindo.lautnusantara.model.SellPriceOther
import com.javaindo.lautnusantara.retrofit.API
import com.javaindo.lautnusantara.utility.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow


class SellingPriceRepository constructor(
    private val api: API
) {

    suspend fun getSellingPrice (searchParm : SearchSellingPriceParmModel)
        : Flow<DataState<ArrayList<PortMapModel>>> = channelFlow {
        send(DataState.Loading)
        delay(1000)
        try{
            send(DataState.Success(processSearchData(searchParm)))
        } catch (e : Exception){
            send(DataState.Error(e))
        }
    }

    private fun processSearchData(parm : SearchSellingPriceParmModel) : ArrayList<PortMapModel> {
        var resultData : ArrayList<PortMapModel> = ArrayList<PortMapModel>()
        val dataListTemp = generateDummy()

        for (i in dataListTemp.indices){
            var portMapModel = PortMapModel()

            if((parm.portName != null && parm.portName.length > 0) ||
                (parm.portName != null && parm.portName.length > 0) ||
                (parm.portName != null && parm.portName.length > 0)){

                if(parm.portName.length > 0){
                    if(parm.portName.equals(dataListTemp.get(i).portMapName,false)){
                        var sellPrices : ArrayList<SellPriceModel> = ArrayList<SellPriceModel>()

                        if(dataListTemp.get(i).sellPriceList != null){
                            val priceList = dataListTemp.get(i).sellPriceList
                            if(parm.dateActivity.length > 0){
                                for(k in priceList.indices){
                                    if(parm.dateActivity.equals(priceList.get(k).dateActivity,false)){
                                        if(parm.fishName.length > 0){
                                            if(parm.fishName.equals(priceList.get(k).animalName,false)){
                                                sellPrices.add(priceList.get(k))
                                            }
                                        } else {
                                            sellPrices.add(priceList.get(k))
                                        }
                                    }
                                }
                            } else {
                                for(k in priceList.indices){
                                    if(parm.fishName.equals(priceList.get(k).animalName,false)){
                                        sellPrices.add(priceList.get(k))
                                    }
                                }
                            }
                        }

                        portMapModel.portMapName = dataListTemp.get(i).portMapName
                        portMapModel.sellPriceList = sellPrices
                        resultData.add(portMapModel)
                    }
                } else if(parm.dateActivity.length > 0){
                    var sellPrices : ArrayList<SellPriceModel> = ArrayList<SellPriceModel>()

                    if(dataListTemp.get(i).sellPriceList != null){
                        val priceList = dataListTemp.get(i).sellPriceList
                        if(parm.dateActivity.length > 0){
                            for(k in priceList.indices){
                                if(parm.dateActivity.equals(priceList.get(k).dateActivity,false)){
                                    if(parm.fishName.length > 0){
                                        if(parm.fishName.equals(priceList.get(k).animalName,false)){
                                            sellPrices.add(priceList.get(k))
                                        }
                                    } else {
                                        sellPrices.add(priceList.get(k))
                                    }
                                }
                            }
                        } else {
                            for(k in priceList.indices){
                                if(parm.fishName.equals(priceList.get(k).animalName,false)){
                                    sellPrices.add(priceList.get(k))
                                }
                            }
                        }
                    }

                    portMapModel.portMapName = dataListTemp.get(i).portMapName
                    portMapModel.sellPriceList = sellPrices
                    resultData.add(portMapModel)
                } else if(parm.fishName.length > 0){
                    var sellPrices : ArrayList<SellPriceModel> = ArrayList<SellPriceModel>()

                    if(dataListTemp.get(i).sellPriceList != null){
                        val priceList = dataListTemp.get(i).sellPriceList
                        for(k in priceList.indices){
                            if(parm.fishName.equals(priceList.get(k).animalName,false)){
                                sellPrices.add(priceList.get(k))
                            }
                        }
                    }

                    portMapModel.portMapName = dataListTemp.get(i).portMapName
                    portMapModel.sellPriceList = sellPrices
                    resultData.add(portMapModel)
                }

            } else {
                portMapModel.portMapName = dataListTemp.get(i).portMapName
                portMapModel.sellPriceList = dataListTemp.get(i).sellPriceList
                resultData.add(portMapModel)
            }
        }

        return resultData
    }

    private fun generateDummy() : ArrayList<PortMapModel>{
        var result : ArrayList<PortMapModel> = ArrayList<PortMapModel>()

        val pp = PortMapModel()
        pp.portMapName = "PP.Brondong"
        var animaList = ArrayList<SellPriceModel>()
        val animal = SellPriceModel()
        animal.animalName = "Krapu"
        animal.traderPrice = "300.000,00"
        animal.amount = "120,00"
        animal.amountProduce = "1,00"
        animal.dateActivity = "2021-12-28"
        animal.positionIndex = 0
        val priceOther = SellPriceOther()
        priceOther.traderPriceOther = "302.000,00"
        priceOther.amountOther = "122,00"
        priceOther.amountProduceOther = "1,00"
        priceOther.positionIndex = 0
        animal.sellPriceOther = priceOther
        animaList.add(animal)

        val animal2 = SellPriceModel()
        animal2.animalName = "Cumi-Cumi"
        animal2.traderPrice = "43.000,00"
        animal2.amount = "410,00"
        animal2.amountProduce = "1,00"
        animal2.dateActivity = "2021-12-28"
        animal2.positionIndex = 1
        val priceOther2 = SellPriceOther()
        priceOther2.traderPriceOther = "44.000,00"
        priceOther2.amountOther = "412,00"
        priceOther2.amountProduceOther = "1,00"
        priceOther2.positionIndex = 1
        animal2.sellPriceOther = priceOther2
        animaList.add(animal2)

        val animal3 = SellPriceModel()
        animal3.animalName = "Kakap Merah"
        animal3.traderPrice = "66.000,00"
        animal3.amount = "20,00"
        animal3.amountProduce = "1,00"
        animal3.dateActivity = "2021-12-27"
        animal3.positionIndex = 2
        val priceOther3 = SellPriceOther()
        priceOther3.traderPriceOther = "68.000,00"
        priceOther3.amountOther = "28,00"
        priceOther3.amountProduceOther = "1,00"
        priceOther3.positionIndex = 2
        animal3.sellPriceOther = priceOther3
        animaList.add(animal3)
        pp.sellPriceList = animaList
        result.add(pp)

        val pp2 = PortMapModel()
        pp2.portMapName = "PP. Tanjung Pandan"
        var animaList2 = ArrayList<SellPriceModel>()
        val animal4 = SellPriceModel()
        animal4.animalName = "Cumi-Cumi"
        animal4.traderPrice = "43.000,00"
        animal4.amount = "410,00"
        animal4.amountProduce = "1,00"
        animal4.dateActivity = "2021-12-26"
        animal4.positionIndex = 3
        val priceOther4 = SellPriceOther()
        priceOther4.traderPriceOther = "46.000,00"
        priceOther4.amountOther = "412,00"
        priceOther4.amountProduceOther = "1,00"
        priceOther4.positionIndex = 3
        animal4.sellPriceOther = priceOther4
        animaList2.add(animal4)

        val animal5 = SellPriceModel()
        animal5.animalName = "Kakap Merah"
        animal5.traderPrice = "66.000,00"
        animal5.amount = "20,00"
        animal5.amountProduce = "1,00"
        animal5.dateActivity = "2021-12-26"
        animal5.positionIndex = 4
        val priceOther5 = SellPriceOther()
        priceOther5.traderPriceOther = "300.000,00"
        priceOther5.amountOther = "120,00"
        priceOther5.amountProduceOther = "1,00"
        priceOther5.positionIndex = 4
        animal5.sellPriceOther = priceOther5
        animaList2.add(animal5)
        pp2.sellPriceList = animaList2
        result.add(pp2)

        val pp3 = PortMapModel()
        pp3.portMapName = "PP. Sadai"
        result.add(pp3)

        val pp4 = PortMapModel()
        pp4.portMapName = "PP. Kwandang"
        result.add(pp4)

        val pp5 = PortMapModel()
        pp5.portMapName = "PP. Sibolga"
        result.add(pp5)

        val pp6 = PortMapModel()
        pp6.portMapName = "PP. Tawang"
        result.add(pp6)

        val pp7 = PortMapModel()
        pp7.portMapName = "PP. Lappa"
        result.add(pp7)

        val pp8 = PortMapModel()
        pp8.portMapName = "PP. Labuhan Lombok"
        result.add(pp8)

        return result
    }

}