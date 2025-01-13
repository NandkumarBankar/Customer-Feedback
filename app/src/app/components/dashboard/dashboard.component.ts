import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { ApiLinks } from '../../api-links';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
  standalone: false
})
export class DashboardComponent implements OnInit {

  feedbackData: any[] = [];
private apiLinks = ApiLinks;

  constructor(private router: Router, private apiService: ApiService) { }
  ngOnInit(): void {
    this.fillData();
  }

  openFeedback(feedback: any) {
    this.router.navigateByUrl('/feedback', { state: { feedback: feedback } });
  }

  fillData() {
    this.apiService.getAll(this.apiLinks.baseLink +'/admin/getall').subscribe(response => {
      console.log(response);
      this.feedbackData = response;
    });
  }
  deleteFeedback(feedBack: any) {
    feedBack.feedback = '';
    this.apiService.update(this.apiLinks.baseLink +'/admin/update/' + feedBack.id, feedBack).subscribe(response => {
      console.log(response);
      this.fillData();
    });
  }

  Logout(){
      this.router.navigateByUrl('/authenticate')
  }
}

