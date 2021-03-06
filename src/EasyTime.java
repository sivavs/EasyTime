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
            if (this.values.length == 0) {
                return 0.00;
            }

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

            if (this.values.length == 0) {
                return 0.00;
            }

            if (this.mean == null) {

                Double total = 0.00;
                for (int i = 0; i < values.length; i++)
                    total += values[i];

                this.mean = total / values.length;

            }

            return this.mean;

        }

        public Double getMedian() {
            if (this.values.length == 0) {
                return 0.00;
            }
            if (median == null) {
                this.median = getPercentile(50.00);
            }

            return this.median;

        }

        public Double getMax() {
            if (this.values.length == 0) {
                return 0.00;
            }
            if (this.max == null) {

                this.max = this.getPercentile(100.00);

            }

            return this.max;

        }

        public Double getPercentile(final Double percentilePoint) {
            if (this.values.length == 0) {
                return 0.00;
            }
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
            if (this.values.length == 0) {
                return 0.00;
            }
            if (this.percentile90 == null) {
                this.percentile90 = this.getPercentile(90.00);
            }

            return percentile90;
        }

    }

    private List<Double> durations;

    private Long previousCheckPoint;

    /*New EasyTime Object */

    public EasyTime() {

        previousCheckPoint = System.currentTimeMillis();

        this.durations = new ArrayList<Double>();
    }

    public void clearAll() {
        this.durations = new ArrayList<Double>();
        previousCheckPoint = System.currentTimeMillis();

    }

    public void clockThis() {
        Long now = System.currentTimeMillis();

        this.durations.add(new Double(previousCheckPoint - now));
        previousCheckPoint = now;

    }

    public void start() {
        previousCheckPoint = System.currentTimeMillis();
    }

    public void resume() {
        previousCheckPoint = System.currentTimeMillis();
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
