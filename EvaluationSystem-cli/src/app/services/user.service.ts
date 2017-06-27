import { Injectable } from '@angular/core';
import { Http} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import {HttpUtilService} from './http-util.service';
import {Router} from '@angular/router';
import {AuthenticationService} from './authentication.service';

@Injectable()
export class UserService {

  constructor( private router: Router,
               private http: Http,
               private httpUtil: HttpUtilService,
               private authentication: AuthenticationService) {
  }


  // GET /api/users/{user_id}
  getUserById(userId: number): Observable<any> {
    return this.http.get( this.httpUtil.url('/api/users/' + userId ),
        this.httpUtil.headers(this.authentication.getToken()) )
      .map( this.httpUtil.extrairDados );
  }
}
