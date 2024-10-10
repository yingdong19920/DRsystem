package cqu.drsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class DRSTest {
    private PrimaryController controller;

    @BeforeEach
    public void setUp() {
        controller = new PrimaryController();
    }

    @Test
    void testReportFireDisaster() {
        controller.reportDisasterForTesting("Fire", "Downtown", "High", "Large building on fire");
        List<Disaster> disasterLog = controller.getDisasterLog();
        assertFalse(disasterLog.isEmpty());
        Disaster reportedDisaster = disasterLog.get(0);
        assertEquals("Fire", reportedDisaster.getType());
        assertEquals("Downtown", reportedDisaster.getLocation());
        assertEquals("High", reportedDisaster.getSeverity());
        assertEquals("Large building on fire", reportedDisaster.getDescription());
    }

    @Test
    void testReportEarthquake() {
        controller.reportDisasterForTesting("Earthquake", "City Center", "High", "Major earthquake, buildings collapsed");
        List<Disaster> disasterLog = controller.getDisasterLog();
        assertFalse(disasterLog.isEmpty());
        Disaster reportedDisaster = disasterLog.get(0);
        assertEquals("Earthquake", reportedDisaster.getType());
        assertEquals("City Center", reportedDisaster.getLocation());
        assertEquals("High", reportedDisaster.getSeverity());
        assertEquals("Major earthquake, buildings collapsed", reportedDisaster.getDescription());
    }

    @Test
    void testReportLowSeverityDisaster() {
        controller.reportDisasterForTesting("Flood", "Riverside", "Low", "Minor flooding in low-lying areas");
        List<Disaster> disasterLog = controller.getDisasterLog();
        assertFalse(disasterLog.isEmpty());
        Disaster reportedDisaster = disasterLog.get(0);
        assertEquals("Flood", reportedDisaster.getType());
        assertEquals("Riverside", reportedDisaster.getLocation());
        assertEquals("Low", reportedDisaster.getSeverity());
        assertEquals("Minor flooding in low-lying areas", reportedDisaster.getDescription());
    }

    @Test
    void testReportWithEmptyFields() {
        controller.reportDisasterForTesting("", "", null, "");
        List<Disaster> disasterLog = controller.getDisasterLog();
        assertTrue(disasterLog.isEmpty());
    }

    @Test
    void testMultipleDisasterReports() {
        controller.reportDisasterForTesting("Fire", "Downtown", "High", "Large building on fire");
        controller.reportDisasterForTesting("Earthquake", "City Center", "High", "Major earthquake, buildings collapsed");
        controller.reportDisasterForTesting("Flood", "Riverside", "Low", "Minor flooding in low-lying areas");
        List<Disaster> disasterLog = controller.getDisasterLog();
        assertEquals(3, disasterLog.size());
    }

    @Test
    void testUnknownDisasterType() {
        controller.reportDisasterForTesting("Alien Invasion", "City Wide", "High", "Unidentified flying objects attacking");
        List<Disaster> disasterLog = controller.getDisasterLog();
        assertFalse(disasterLog.isEmpty());
        Disaster reportedDisaster = disasterLog.get(0);
        assertEquals("Alien Invasion", reportedDisaster.getType());
        assertEquals("City Wide", reportedDisaster.getLocation());
        assertEquals("High", reportedDisaster.getSeverity());
        assertEquals("Unidentified flying objects attacking", reportedDisaster.getDescription());
    }
}