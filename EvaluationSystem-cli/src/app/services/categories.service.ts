import { Injectable } from '@angular/core';
import { Http} from '@angular/http';
import { Observable } from 'rxjs/Observable';
import {HttpUtilService} from './http-util.service';
import {Router} from '@angular/router';

@Injectable()
export class CategoriesService {

  constructor(private router: Router,
              private http: Http,
              private httpUtil: HttpUtilService) {
  }

  // GET /api/classes/{class_id}/categories
  getCategoriesByClasse(classeId: number): Observable<any> {
    return this.http.get( this.httpUtil.url('/api/classes/' + classeId + '/categories' ), this.httpUtil.headers() )
      .map( this.httpUtil.extrairDados );
  }

}
