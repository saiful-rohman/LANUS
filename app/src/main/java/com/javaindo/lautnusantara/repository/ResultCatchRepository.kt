package com.javaindo.lautnusantara.repository

import com.javaindo.lautnusantara.model.ResultCatchModel
import com.javaindo.lautnusantara.model.SearchResultCatchParmModel
import com.javaindo.lautnusantara.retrofit.API
import com.javaindo.lautnusantara.utility.DataState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ResultCatchRepository constructor(
    private val api: API,
) {

    suspend fun getResultCatch(searchParm: SearchResultCatchParmModel)
    : Flow<DataState<ArrayList<ResultCatchModel>>> = flow{
        emit(DataState.Loading)
        delay(1000)
        try{
            emit(DataState.Success(processData(searchParm)))
        }catch (e : Exception){
            emit(DataState.Error(e))
        }
    }

    private fun processData(parm: SearchResultCatchParmModel): ArrayList<ResultCatchModel>{
        var resultData : ArrayList<ResultCatchModel> = ArrayList<ResultCatchModel>()
        val dataDummies = generateDummy()

        for(i in dataDummies.indices){
            var resultCatch = ResultCatchModel()

            if(parm.dateFrom != null && parm.dateFrom.length > 0 ||
                parm.dateTo != null && parm.dateTo.length > 0){

                val datefrom = parm.dateFrom.replace("-","")
                val dateTo = parm.dateTo.replace("-","")
                val dateSearch = dataDummies.get(i).dateSearch.replace("-","")

                if(datefrom.toInt() <= dateSearch.toInt() && dateTo.toInt() >= dateSearch.toInt()){

                    resultCatch = dataDummies.get(i)
                    resultData.add(resultCatch)

                }

            } else {
                resultCatch = dataDummies.get(i)
                resultData.add(resultCatch)
            }

        }

        return resultData
    }

    private fun generateDummy() : ArrayList<ResultCatchModel>{
        var result : ArrayList<ResultCatchModel> = ArrayList<ResultCatchModel>()

        val rc = ResultCatchModel()
        rc.dateSearch = "2022-01-05"
        rc.dateProc = "Wed, 01 Dec 21 20:41:38"
        rc.animalName = "Kerapu Karang"
        rc.weightCapture = "1"
        rc.price = "30.000"
        rc.positionIndex = 0
        result.add(rc)

        val rc2 = ResultCatchModel()
        rc2.dateSearch = "2022-01-05"
        rc2.dateProc = "Wed, 02 Dec 22 20:41:38"
        rc2.animalName = "Aru dobo"
        rc2.weightCapture = "20"
        rc2.price = "32.000"
        rc2.positionIndex = 1
        result.add(rc2)

        val rc3 = ResultCatchModel()
        rc3.dateSearch = "2022-01-06"
        rc3.dateProc = "Wed, 03 Dec 23 20:41:38"
        rc3.animalName = "Tenggiri"
        rc3.weightCapture = "0"
        rc3.price = "0"
        rc3.positionIndex = 2
        result.add(rc3)

        val rc4 = ResultCatchModel()
        rc4.dateSearch = "2022-01-07"
        rc4.dateProc = "Wed, 03 Dec 24 20:41:38"
        rc4.animalName = "Tuna Mata Biasa"
        rc4.weightCapture = "50"
        rc4.price = "25.000"
        rc4.positionIndex = 3
        result.add(rc4)

        val rc5 = ResultCatchModel()
        rc5.dateSearch = "2021-01-08"
        rc5.dateProc = "Wed, 04 Dec 25 20:41:38"
        rc5.animalName = "Ikan Tuna"
        rc5.weightCapture = "0"
        rc5.price = "0"
        rc5.positionIndex = 4
        result.add(rc5)

        return result
    }

}