import { Component } from '@angular/core';
import { ParcoursListComponent } from './components/parcours-list/parcours-list.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, ParcoursListComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'OmniTrack';
}