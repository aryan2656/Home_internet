package org.example.home_internet_hero.repository;

import org.example.home_internet_hero.Plans.model.Plans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plans, Long> {
}
