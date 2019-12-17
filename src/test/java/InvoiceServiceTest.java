import org.junit.Assert;
import org.junit.Test;

public class InvoiceServiceTest {

    @Test
    public void givenDistanceAndTime_ShouldReturnTotalFare() {
        double distance = 2.0;
        int time = 5;
        InvoiceService invoiceService = new InvoiceService(InvoiceService.RideType.NORMAL_RIDE);
        double fare = invoiceService.calculateFare(distance, time);
        Assert.assertEquals(25.0, fare, 0.0);
    }

    @Test
    public void givenLessDistanceOrTime_ShouldReturnMinFare() {
        double distance = 0.1;
        int time = 1;
        InvoiceService invoiceService = new InvoiceService(InvoiceService.RideType.NORMAL_RIDE);
        double fare = invoiceService.calculateFare(distance, time);
        Assert.assertEquals(5.0, fare, 0.0);
    }

    @Test
    public void givenMultipleRides_ShouldReturnTotalFare() {
        Ride[] ride = {new Ride(2.0, 5),
                new Ride(0.1, 1)
        };
        InvoiceService invoiceService = new InvoiceService(InvoiceService.RideType.NORMAL_RIDE);
        InvoiceSummary invoiceSummary = invoiceService.calculateFare(ride);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30.0);
        Assert.assertEquals(expectedInvoiceSummary, invoiceSummary);
    }

    @Test
    public void givenUserIdAndRides_ShouldReturnsInvoiceSummary_ForNormal() {
        String userId = "a@b.commm";
        Ride[] ride = {new Ride(2.0, 5),
                new Ride(0.1, 1)
        };
        InvoiceService invoiceService = new InvoiceService(InvoiceService.RideType.NORMAL_RIDE);
        invoiceService.addRides(userId, ride);
        InvoiceSummary invoiceSummary = invoiceService.getInvoiceSummary(userId);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30.0);
        Assert.assertEquals(expectedInvoiceSummary, invoiceSummary);
    }

    @Test
    public void givenUserIdAndRides_ShouldReturnsInvoiceSummary_ForPremium() {
        String userId = "a@b.commm";
        Ride[] ride = {new Ride(2.0, 5),
                new Ride(0.1, 1)
        };
        InvoiceService invoiceService = new InvoiceService(InvoiceService.RideType.PREMIUM_RIDE);
        invoiceService.addRides(userId, ride);
        InvoiceSummary invoiceSummary = invoiceService.getInvoiceSummary(userId);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 60.0);
        Assert.assertEquals(expectedInvoiceSummary, invoiceSummary);
    }

    @Test
    public void givenUserIdAndRides_ShouldReturnsInvoiceSummary_ForPremium_MultiValue() {
        String userId = "a@b.commm";
        Ride[] ride = {new Ride(2.0, 5),
                new Ride(0.1, 1)
        };
        String userId1 = "suraj";
        Ride[] ride1 = {new Ride(3.0, 5),
                new Ride(0.1, 1)
        };
        InvoiceService invoiceService = new InvoiceService(InvoiceService.RideType.PREMIUM_RIDE);
        invoiceService.addRides(userId, ride);
        invoiceService.addRides(userId1, ride1);
        InvoiceSummary invoiceSummary1 = invoiceService.getInvoiceSummary(userId1);
        InvoiceSummary expectedInvoiceSummary1 = new InvoiceSummary(2, 75.0);
        Assert.assertEquals(expectedInvoiceSummary1, invoiceSummary1);
        InvoiceSummary invoiceSummary = invoiceService.getInvoiceSummary(userId);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 60.0);
        Assert.assertEquals(expectedInvoiceSummary, invoiceSummary);
    }

    @Test
    public void givenUserIdAndRides_ShouldReturnsInvoiceSummary_ForNormal_multiMonths() {
        String userId = "a@b.commm";
        Ride[] ride = {new Ride(3.0, 5),
                new Ride(0.1, 1),
         };
        Ride[] ride1 = {new Ride(2.0, 5),
                new Ride(0.1, 1)
        };
        InvoiceService invoiceService = new InvoiceService(InvoiceService.RideType.PREMIUM_RIDE);
        invoiceService.addRides(userId, ride);
        InvoiceSummary invoiceSummary = invoiceService.getInvoiceSummary(userId);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(4, 135.0);
        Assert.assertEquals(expectedInvoiceSummary, invoiceSummary);
    }
}
