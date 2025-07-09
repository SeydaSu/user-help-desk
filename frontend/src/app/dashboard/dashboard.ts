import { CommonModule } from '@angular/common';
import { Component, computed, input } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  imports: [RouterOutlet, CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard {
  isLeftSideBarCollapsed = input.required<boolean>();
  screenWidth = input.required<number>();
  sizeClass = computed(() => {
    const isLeftSideBarCollapsed = this.isLeftSideBarCollapsed();
    if(isLeftSideBarCollapsed) {
      return  ' ';
    }
    return this.screenWidth() > 768 ? 'body-trimmed' : 'body-md-screen'
  })
}
