import { Component, Inject, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { Router } from '@angular/router';
import { ApiLinks } from '../../api-links';


@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrl: './dialog.component.css',
  standalone: false
})
export class DialogComponent implements OnInit {
  isAdmin: boolean = false;
  feedback: any;
  feedbackText: string = '';
  private apiLinks = ApiLinks;

  constructor(private apiService: ApiService,
    private router: Router,
  ) {
  }

  ngOnInit(): void {
    this.feedback ??= history.state.feedback;
    if (this.feedback) {
      this.feedbackText = this.feedback.feedback;
    }
    this.isAdmin = (sessionStorage.getItem('#area67') || '') == 'Yes';
    console.log(this.feedback);
    debugger
  }

  update() {
    this.feedback.feedback = this.feedbackText;
    if (this.isAdmin) {
      this.apiService.update(this.apiLinks.baseLink +'/admin/update/' + this.feedback.id, this.feedback).subscribe((response: any) => {
        console.log(response);
        this.router.navigateByUrl('/dashboard');
        alert('User ' + response.detail.userName + ' updated Successfully')
      })
    }
    this.apiService.update(this.apiLinks.baseLink +'/user/update/' + this.feedback.id, this.feedback).subscribe((response: any) => {
      alert('User ' + response.detail.userName + ' updated Successfully')
      console.log(response);
    });
  }
  cancel() {
    this.router.navigateByUrl('/dashboard');
  }
}
