//package org.example.home_internet_hero.service;
//
//import org.example.home_internet_hero.Plans.model.Plans;
//import org.example.home_internet_hero.Plans.service.PlanService;
//import org.example.home_internet_hero.Plans.repository.PlanRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class PlanServiceTest {
//
//    @Mock
//    private PlanRepository planRepository;
//
//    @InjectMocks
//    private PlanService planService;
//
//    @Test
//    void testAddPlan() {
//        Plans plan = new Plans("AT&T", "300Mbps", "$55/month", "300Mbps", "N/A", "Features", "url-to-image");
//        when(planRepository.save(plan)).thenReturn(plan);
//
//        Plans savedPlan = planService.addPlan(plan);
//        assertNotNull(savedPlan);
//        assertEquals("AT&T", savedPlan.getProvider());
//    }
//
//    // Add more tests for other methods...
//}
