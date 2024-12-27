import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  constructor(private http: HttpClient) { }


  private getAuthToken() {
    let auth_token = sessionStorage.getItem('#area61');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${auth_token}`
    });
    return headers;
  }

  login(url: string, data: any) {
    return this.http.post(url, data);
  }

  getAll(url: string) {
    const headers = this.getAuthToken();
    return this.http.get<any>(url, { headers });
  }

  get(url: string) {
    const headers = this.getAuthToken();
    return this.http.get<any>(url, { headers });
  }

  delete(url: string) {
    const headers = this.getAuthToken();
    return this.http.delete(url, { headers });
  }

  post(url: string, data: any) {
    const headers = this.getAuthToken();
    return this.http.post(url, data, { headers });
  }

  update(url: string, data: any) {
    const headers = this.getAuthToken();
    return this.http.put(url, data, { headers });
  }


}
