import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-authenticate-page',
  templateUrl: './authenticate-page.component.html',
  styleUrl: './authenticate-page.component.css',
  standalone: false
})
export class AuthenticatePageComponent implements OnInit {
  loginRequest!: any;
  signUpRequest!: any;
  isLogin: boolean = true;
  error: boolean = false;

  constructor(private fb: FormBuilder,
    private apiService: ApiService,
    private router: Router,) {
  }


  ngOnInit(): void {
    this.loginRequest = this.fb.group({
      userName: ["", Validators.required],
      password: ["", Validators.required],
    });

    this.signUpRequest = this.fb.group({
      userName: '',
      password: '',
      email: ['', [Validators.required, Validators.email]],
      confirmPassword: '',
    });
  }

  login() {
    console.log(this.loginRequest.value);
    this.apiService.login('http://localhost:8080/uam/auth/login', this.loginRequest.value).subscribe((responseLogin: any) => {
      if (responseLogin.token !== 'User Not Found') {
        sessionStorage.setItem('#area61', responseLogin.token);
        this.apiService.get('http://localhost:8080/uam/auth/getClaim').subscribe(response => {
          const roleAdmin = (response['user Role'] == 'ROLE_admin');
          sessionStorage.setItem('#area67', roleAdmin ? 'Yes' : 'No');
          sessionStorage.setItem('#area68', response.user);
          this.updateFeedback(roleAdmin);
        });
      } else {
        alert('User Not Found');
      }
    })
  }
  updateFeedback(roleAdmin: any) {
    const url: string = roleAdmin ? '/dashboard' : '/feedback';
    if (roleAdmin) {
      this.router.navigateByUrl(url);
    } else {
      this.apiService.get('http://localhost:8080/uam/user/get/' + sessionStorage.getItem('#area68')).subscribe((response: any) => {
        this.router.navigateByUrl(url, { state: { feedback: response } });
      })
    }
  }

  signUp() {
    if (this.signUpRequest.value.userName.length != 0) {
      console.log(this.signUpRequest.value);
      this.apiService.login('http://localhost:8080/uam/auth/create', this.signUpRequest.value).subscribe((response: any) => {
        console.log(response);
        if (response.massage !== 'User Already Exist') {
          alert("User Created Successfully");
          this.isLogin = true  // redirct to login while user Sign up 
        } else {
          alert('User Already Exist');
        }
      }, error => {
        alert(error);
      });
    }else{
      alert('Insert User Name')
    }
   
  }
  confirmPassword(a: string, b: string) {
    this.error = (a !== b);
    console.log(a, b);
  }

}
