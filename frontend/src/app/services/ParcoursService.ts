import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Parcours } from '../models/parcours.model';

@Injectable({
  providedIn: 'root'
})
export class ParcoursService {

  private apiUrl = 'http://localhost:8081/api/parcours';

  constructor(private http: HttpClient) {}

  /* GET - Récupérer tous les parcours */
  getAllParcours(): Observable<Parcours[]> {
    return this.http.get<Parcours[]>(this.apiUrl);
  }

  getParcoursById(id: number): Observable<Parcours>
  {
    return this.http.get<Parcours>(`${this.apiUrl}/${id}`);
  }

  getParcoursByClient(clientId: string) : Observable<Parcours[]> {
    return this.http.get<Parcours[]>(`${this.apiUrl}/client/${clientId}`);
  }

  getParcoursByStatus(status: string): Observable<Parcours[]> {
    return this.http.get<Parcours[]>(`${this.apiUrl}/status/${status}`);
  }

  
  /* GET - Récupérer les parcours par journeyType */
  getParcoursByJourneyType(journeyType: string): Observable<Parcours[]> {
    return this.http.get<Parcours[]>(`${this.apiUrl}/type/${journeyType}`);
  }

  /* GET - Récupérer les parcours par channel */
  getParcoursByChannel(channel: string): Observable<Parcours[]> {
    return this.http.get<Parcours[]>(`${this.apiUrl}/channel/${channel}`);
  }

  /* POST - Créer un parcours */
  createParcours(parcours: Parcours): Observable<Parcours> {
    return this.http.post<Parcours>(this.apiUrl, parcours);
  }

  /* PUT - Interrompre un parcours */
  interruptParcours(id: number): Observable<Parcours> {
    return this.http.put<Parcours>(`${this.apiUrl}/${id}/interrupt`, {});
  }

  /* PUT - Reprendre un parcours */
  resumeParcours(id: number): Observable<Parcours> {
    return this.http.put<Parcours>(`${this.apiUrl}/${id}/resume`, {});
  }

  /* PUT - Terminer un parcours */
  completeParcours(id: number): Observable<Parcours> {
    return this.http.put<Parcours>(`${this.apiUrl}/${id}/complete`, {});
  }


}