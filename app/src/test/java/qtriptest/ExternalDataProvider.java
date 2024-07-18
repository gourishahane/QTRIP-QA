package qtriptest;

import org.testng.annotations.DataProvider;

public class ExternalDataProvider {

    @DataProvider(name = "qTripData")
    public Object[][] provideData() throws Exception {
        DP dataProviderUtility = new DP();
        return dataProviderUtility.dpMethod("TestCase01"); // Pass the sheet name here
    }
}
