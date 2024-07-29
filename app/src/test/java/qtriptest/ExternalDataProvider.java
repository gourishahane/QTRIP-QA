package qtriptest;


import org.testng.annotations.DataProvider;

public class ExternalDataProvider {

    @DataProvider(name = "qTripDataForTestCase01")
    public Object[][] provideDataForTestCase01() throws Exception {
        DP dataProviderUtility = new DP();
        return dataProviderUtility.dpMethod("TestCase01"); 
    }

    
    @DataProvider(name = "qTripDataForTestCase02")
    public Object[][] provideDataForTestCase02() throws Exception {
        DP dataProviderUtility = new DP();
        return dataProviderUtility.dpMethod("TestCase02"); 
    }

     
    @DataProvider(name = "qTripDataForTestCase03")
    public Object[][] provideDataForTestCase03() throws Exception {
        DP dataProviderUtility = new DP();
        return dataProviderUtility.dpMethod("TestCase03"); 
    }

    @DataProvider(name = "qTripDataForTestCase04")
    public Object[][] provideDataForTestCase04() throws Exception {
        DP dataProviderUtility = new DP();
        return dataProviderUtility.dpMethod("TestCase04"); 
    }

}

