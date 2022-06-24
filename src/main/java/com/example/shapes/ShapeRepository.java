package com.example.shapes;

import org.springframework.data.jpa.repository.JpaRepository;

interface ShapeRepository extends JpaRepository<Shape, Long> {

}
