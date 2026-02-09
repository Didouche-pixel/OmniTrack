import { Component, OnInit } from '@angular/core';
import { Parcours } from '../../models/parcours.model';
import { CommonModule } from '@angular/common';
import { ParcoursService } from '../../services/ParcoursService';

@Component({
  selector: 'app-parcours-list',
standalone: true,  // ← AJOUTE ÇA
  imports: [CommonModule],  // ← AJOUTE ÇA
  templateUrl: './parcours-list.component.html',
  styleUrls: ['./parcours-list.component.css']
})
export class ParcoursListComponent implements OnInit {

  parcoursList: Parcours[] = [];

  constructor(private parcoursService: ParcoursService) {}

  ngOnInit(): void {
    this.loadAllParcours();
  }

  loadAllParcours(): void {
    this.parcoursService.getAllParcours().subscribe({
      next: (data) => {
        this.parcoursList = data;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des parcours:', err);
      }
    });
  }
}