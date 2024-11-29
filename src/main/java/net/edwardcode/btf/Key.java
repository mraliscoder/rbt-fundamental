package net.edwardcode.btf;

public record Key(GroupEducationType type, int num) {
    /**
     * Add lead zeroes to integer part of group key
     * @return formatted string
     */
    public String addLeadZeros() {
        return String.format("%04d", num);
    }

    /**
     * Convert string value of key into Key object
     * @param str string key value
     * @return an object of key
     * @throws InvalidKeyException key is in invalid format
     */
    public static Key parseKey(String str) throws InvalidKeyException {
        if (!Utils.validateName(str)) {
            throw new InvalidKeyException("String is not a valid key: " + str);
        }

        GroupEducationType groupType = GroupEducationType.getType(str.charAt(0));
        int num = Integer.parseInt(str.substring(1));
        return new Key(groupType, num);
    }

    /**
     * Compare two keys
     * @param k1 first key
     * @param k2 second key
     * @return -1 if k1 is lower than k2, 0 if equal, 1 if k2 is lower than k1
     */
    public static int compare(Key k1, Key k2) {
        if (k1.type.getType() < k2.type.getType()) {
            return -1;
        }
        if (k1.type.getType() > k2.type.getType()) {
            return 1;
        }

        return Integer.compare(k1.num, k2.num);
    }

    @Override
    public String toString() {
        return type.getType() + addLeadZeros();
    }
}
