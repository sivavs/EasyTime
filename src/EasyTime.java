import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by siva.vadivel on 4/29/16.
 */
public class EasyTime {

    public static class StatResults {
        private Double mean;
        private Double median;
        private Double percentile90;
        private Double max;

        private Double[] values;

        private Double standardDeviation;

        public StatResults(Double[] values) {
            this.values = values;
            this.mean = null;
            this.median = null;
            this.percentile90 = null;
            this.max = null;
        }

        public double getStandardDeviation() {
            if (standardDeviation == null) {
                double numerator = 0.00;
                for (Double l : values) {
                    numerator += Math.pow((l - mean), 2.0);
                }
                double denominator = (double) (values.length);
                this.standardDeviation = Math.pow(numerator / denominator, 0.5);
            }

            return standardDeviation;
        }

        public Double getMean() {

            if (this.mean == null) {

                Double total = 0.00;
                for (int i = 0; i < values.length; i++)
                    total += values[i];

                this.mean = total / values.length;

            }

            return this.mean;

        }

        public Double getMedian() {

            if (median == null) {
                this.median = getPercentile(50.00);
            }

            return this.median;

        }

        public Double getMax() {

            if (this.max == null) {

                this.max = this.getPercentile(100.00);

            }

            return this.max;

        }

        public Double getPercentile(final Double percentilePoint) {

            Double internalPercentilePoint = new Double(percentilePoint);
            if (internalPercentilePoint > 100)
                internalPercentilePoint = 100.00;
            if (internalPercentilePoint < 0)
                internalPercentilePoint = 0.00;

            int percentilePosition = 0;
            Arrays.sort(this.values);
            double percentilePercentage = internalPercentilePoint / 100.00;
            int listLength = this.values.length;
            percentilePosition = (int) Math
                    .round((percentilePercentage * (double) listLength) + .5) - 1;

            percentilePosition = Math.min(listLength - 1, percentilePosition);

            return this.values[percentilePosition];

        }

        public Double getPercentile90() {
            if (this.percentile90 == null) {
                this.percentile90 = this.getPercentile(90.00);
            }

            return percentile90;
        }

    }

    private List<Double> durations;
    private List<Long> checkPoints;

    /*New EasyTime Object */

    public EasyTime() {
        this.durations = new ArrayList<Double>();
        this.checkPoints = new ArrayList<Long>();
        checkPoints.add(System.currentTimeMillis());
    }

    public void clearAll() {
        this.durations = new ArrayList<Double>();
        this.checkPoints = new ArrayList<Long>();
        checkPoints.add(System.currentTimeMillis());

    }

    public void clockThis() {
        this.checkPoints.add(System.currentTimeMillis());
        this.durations.add(new Double(checkPoints.get(checkPoints.size() - 1) - checkPoints.get(checkPoints.size() - 2)));

    }

    public void start() {
        this.checkPoints.add(System.currentTimeMillis());
    }

    public void resume() {
        this.checkPoints.add(System.currentTimeMillis());
    }

    public void pause() {
        this.clockThis();
    }

    public void stop() {
        this.clockThis();
    }

    public StatResults getStatResults() {
        return new StatResults(durations.toArray(new Double[durations.size()]));
    }

    public void printStatResults(String title) {
        System.out.println("Timing stats for " + title);
        StatResults statResults = this.getStatResults();
        System.out.println("Mean : " + statResults.getMean());
        System.out.println("Median : " + statResults.getMedian());
        System.out.println("Standard Deviation : " + statResults.getStandardDeviation() + " (" + statResults.getStandardDeviation() / statResults.getMean() * 100 + "%)");
        System.out.println("90th Percentile : " + statResults.getPercentile90());

    }

}
