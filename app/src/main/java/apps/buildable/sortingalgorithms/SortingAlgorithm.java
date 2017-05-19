package apps.buildable.sortingalgorithms;

/**
 * Created by SamerGigaByte on 5/19/2017.
 */

public enum SortingAlgorithm {
    QuickSort("1");
    private final String value;

    SortingAlgorithm(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public SortingAlgorithm getAlgorithm(String value) {
        for (SortingAlgorithm type :
                SortingAlgorithm.values()) {
            if (type.getValue().equals(value))
                return type;
        }
        return null;
    }
}
