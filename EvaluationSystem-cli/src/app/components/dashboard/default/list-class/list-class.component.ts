import {AfterViewInit, Component, OnInit} from '@angular/core';
import {GroupService} from '../../../../services/group.service';
import {AuthenticationService} from '../../../../services/authentication.service';
import {Group} from '../../../../models/group';
import {Class} from '../../../../models/class';
import {User} from '../../../../models/user';

declare var x_navigation: any;
declare var page_content_onresize:  any;

@Component({
  selector: 'app-list-class',
  templateUrl: './list-class.component.html',
  styleUrls: ['./list-class.component.css']
})
export class ListClassComponent implements OnInit, AfterViewInit {

  allGroups: Array<Group>;

  constructor(private group: GroupService,
              private authentication: AuthenticationService) {
    this.allGroups = new Array<Group>();
  }

  ngOnInit() {
    this.getAllGroups();
  }

  ngAfterViewInit() {
    x_navigation();
    page_content_onresize();
  }

  private getAllGroups() {
    this.group.getGroupByUser(this.authentication.getUserId()).subscribe(
      resultado => {
        for ( const group of resultado){
          this.allGroups.push(this.createGroup(group));
        }
        page_content_onresize();
      },
      error => {
        console.log(error);
      }
    );
  }

  private createGroup(groupT): Group {
    const teacher = groupT._class.teacher;
    const _teacher: User =  new User( teacher.id, teacher.email, teacher.firstName, teacher.lastName, teacher.type, '');
    const classe = groupT._class;
    const _classe = new Class( classe.name, classe.abbreviation);
    _classe.id = classe.id;
    _classe.user = _teacher;
    const group = groupT;
    const _group = new Group(group.name);
    _group.id = group.id;
    _group.class = _classe;
    return _group;
  }

  protected deleteGroup( id: string ): void {
    // TODO fazer delete do group
  }

  protected  editGroup( id: string ): void {
    // TODO fazer edit do group
  }

}
