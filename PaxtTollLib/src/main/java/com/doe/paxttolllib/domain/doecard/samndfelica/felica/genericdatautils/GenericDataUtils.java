package com.doe.paxttolllib.domain.doecard.samndfelica.felica.genericdatautils;

import com.doe.paxttolllib.domain.doecard.samndfelica.Utils;
import com.doe.paxttolllib.domain.models.genericdata.GenericDataPojo;
import com.doe.paxttolllib.domain.models.genericdata.GenericDataPojoResponse;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GenericDataUtils {

    public static GenericDataPojoResponse getGenericDataPojoResponse(byte[] data) {
        List<GenericDataPojo> list = new LinkedList<>();

        GenericDataPojo modelClass = new GenericDataPojo();

        modelClass.setCardName_String( new Utils().hexToString(
                Utils.bytesToHexString(
                        Arrays.copyOfRange(
                                data,
                                0,
                                48
                        )
                )
        ));

        modelClass.setMobileNumber_Long(Utils.bytesToLong(Arrays.copyOfRange(data, 84, 90), 6));
        modelClass.setAadhaarNumber_Long(Utils.bytesToLong(Arrays.copyOfRange(data, 90, 95), 5));
        modelClass.setCardNumber_Long(Utils.bytesToLong(Arrays.copyOfRange(data, 96, 112), 16));
        modelClass.setCardBalance_Int(ByteBuffer.wrap(Arrays.copyOfRange(data, 112, 116))
                .order(ByteOrder.LITTLE_ENDIAN).getInt());

        list.add(modelClass);

        return getGenericDataPojoResponse(list);
    }

    private static GenericDataPojoResponse getGenericDataPojoResponse(List<GenericDataPojo> list) {
        GenericDataPojoResponse response = new GenericDataPojoResponse();
        response.setGenericDataPojoList(list);
        response.setMessage("success");
        return response;
    }

    public static GenericDataPojoResponse setGenericDataPojoResponseError(String message) {
        GenericDataPojoResponse transactionalLogsResponse = new GenericDataPojoResponse();
        transactionalLogsResponse.setMessage(message);
        transactionalLogsResponse.setGenericDataPojoList(null);
        return transactionalLogsResponse;
    }
}
