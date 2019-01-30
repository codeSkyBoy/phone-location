package net.wy.phone;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author WY
 */
public class PhoneLocationSearch {

    private static final String[] PHONE_NUMBER_TYPE = {null, "中国移动", "中国联通", "中国电信"};
    private static final int INDEX_SEGMENT_LENGTH = 9;
    private static final int DATA_FILE_LENGTH_HINT = 3670016;
    private static final String PHONE_DAT_FILE_PATH = "phone.dat";

    private byte[] dataByteArray;
    private int indexAreaOffset;
    private int phoneRecordCount;

    public PhoneLocationSearch() {
        initData();
    }

    /**
     * 加载数据文件到内存
     */
    private void initData() {
        if (dataByteArray == null) {
            ByteArrayOutputStream byteData = new ByteArrayOutputStream(DATA_FILE_LENGTH_HINT);
            byte[] buffer = new byte[1024];
            int readBytesLength;
            try (InputStream inputStream = PhoneLocationSearch.class.getClassLoader().getResourceAsStream(PHONE_DAT_FILE_PATH)) {
                while ((readBytesLength = inputStream.read(buffer)) != -1) {
                    byteData.write(buffer, 0, readBytesLength);
                }
            } catch (Exception e) {
                System.err.println("Can't find phone.dat in classpath: " + PHONE_DAT_FILE_PATH);
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            dataByteArray = byteData.toByteArray();
            indexAreaOffset = byteArrayToInt(Arrays.copyOfRange(dataByteArray, 4, 8));
            phoneRecordCount = (dataByteArray.length - indexAreaOffset) / INDEX_SEGMENT_LENGTH;
        }
    }

    /**
     * 二分查找
     */
    public LocationInfo lookup(String phoneNumber) {
        String numSegment = getNumSegment(phoneNumber);
        if (numSegment == null) {
            return null;
        }
        int phoneNumberPrefix = Integer.parseInt(numSegment);
        int left = 0;
        int right = phoneRecordCount;
        while (left <= right) {
            int middle = (left + right) >> 1;
            int currentOffset = indexAreaOffset + middle * INDEX_SEGMENT_LENGTH;
            if (currentOffset >= dataByteArray.length) {
                return null;
            }

            int currentPrefix = byteArrayToInt(Arrays.copyOfRange(dataByteArray, currentOffset, currentOffset + 4));
            if (currentPrefix > phoneNumberPrefix) {
                right = middle - 1;
            } else if (currentPrefix < phoneNumberPrefix) {
                left = middle + 1;
            } else {
                int infoBeginOffset = byteArrayToInt(Arrays.copyOfRange(dataByteArray, currentOffset + 4, currentOffset + 8));
                int phoneType = dataByteArray[currentOffset + 8];

                int infoLength = -1;
                for (int i = infoBeginOffset; i < indexAreaOffset; ++i) {
                    if (dataByteArray[i] == 0) {
                        infoLength = i - infoBeginOffset;
                        break;
                    }
                }

                String infoString = new String(dataByteArray, infoBeginOffset, infoLength, StandardCharsets.UTF_8);
                String[] infoSegments = infoString.split("\\|");

                LocationInfo info = new LocationInfo();
                info.setPhoneNumber(phoneNumber);
                info.setProvince(infoSegments[0]);
                info.setCity(infoSegments[1]);
                info.setProvinceAdCode(infoSegments[2]);
                info.setProvinceCenter(infoSegments[3]);
                info.setCityCode(infoSegments[4]);
                info.setCityAdCode(infoSegments[5]);
                info.setCityCenter(infoSegments[6]);
                info.setIsoCode(infoSegments[7]);
                info.setOperator(PHONE_NUMBER_TYPE[phoneType]);
                return info;
            }
        }
        return null;
    }

    /**
     * 解析号段
     */
    private String getNumSegment(String number) {
        if (number.startsWith("86")) {
            number = number.substring(2);
        }
        if (number.startsWith("086") || number.startsWith("+86")) {
            number = number.substring(3);
        }
        //无效手机号过滤
        String checkFormat = "[0-9]*";
        boolean checkFlag = Pattern.compile(checkFormat).matcher(number).matches();
        if (!checkFlag) {
            return null;
        }
        //手机号
        String mobileFormat = "[0]?1[1-9][0-9]{9}";
        boolean mobileFlag = Pattern.compile(mobileFormat).matcher(number).matches();
        if (mobileFlag) {
            if (number.startsWith("0")) {
                number = number.substring(1);
            }
            return number.substring(0, 7);
        }
        //座机号
        String planeFormat = "0[0-9]{2,3}[0-9]{7,}";
        boolean planeFlag = Pattern.compile(planeFormat).matcher(number).matches();
        if (planeFlag) {
            if (number.startsWith("01") || number.startsWith("02")) {
                return number.substring(0, 3);
            } else {
                return number.substring(0, 4);
            }
        }
        return number;
    }

    /**
     * 字节数组 to Int
     */
    private int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }
}
