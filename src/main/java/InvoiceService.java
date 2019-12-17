public class InvoiceService {
    private static int COST_PER_TIME;
    private static double MINIMUM_COST_PER_KILOMETER;
    private static double MIN_FARE;
    private RideRepository rideRepository;

    public enum RideType {
        NORMAL_RIDE, PREMIUM_RIDE
    }

    public InvoiceService(RideType type) {
        this.rideRepository = new RideRepository();
        if (type.equals(RideType.NORMAL_RIDE)) {
            MINIMUM_COST_PER_KILOMETER = 10;
            COST_PER_TIME = 1;
            MIN_FARE = 5;
        } else if (type.equals(RideType.PREMIUM_RIDE)) {
            MINIMUM_COST_PER_KILOMETER = 15;
            COST_PER_TIME = 2;
            MIN_FARE = 20;
        }
    }

    public double calculateFare(double distance, int time) {
        double totalFare = distance * MINIMUM_COST_PER_KILOMETER + time * COST_PER_TIME;
        return Math.max(totalFare, MIN_FARE);
    }

    public InvoiceSummary calculateFare(Ride[] ride) {
        double totalFare = 0;
        for (Ride rides : ride) {
            totalFare += this.calculateFare(rides.distance, rides.time);
        }
        return new InvoiceSummary(ride.length, totalFare);
    }

    public InvoiceSummary getInvoiceSummary(String userId) {
        return this.calculateFare(rideRepository.getRide(userId));
    }

    public void addRides(String userId, Ride[] ride) {
        rideRepository.addRides(userId, ride);
    }
}
