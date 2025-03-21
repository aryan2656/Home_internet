package org.example.home_internet_hero.Plans.service;

import org.example.home_internet_hero.Plans.model.Plans;
import org.example.home_internet_hero.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    // Get all plans
    public List<Plans> getAllPlans() {
        return planRepository.findAll();
    }

    // Get a plan by its ID
    public Plans getPlanById(Long id) {
        Optional<Plans> plan = planRepository.findById(id);
        return plan.orElse(null); // Returns null if not found
    }

    // Add a new plan
    public Plans addPlan(Plans plan) {
        return planRepository.save(plan);
    }

    // Update an existing plan
    public Plans updatePlan(Long id, Plans plan) {
        if (planRepository.existsById(id)) {
            plan.setId(id);
            return planRepository.save(plan);
        }
        return null;
    }

    // Delete a plan
    public String deletePlan(Long id) {
        if (planRepository.existsById(id)) {
            planRepository.deleteById(id);
            return "Plan removed successfully.";
        }
        return "Plan not found.";
    }
}
