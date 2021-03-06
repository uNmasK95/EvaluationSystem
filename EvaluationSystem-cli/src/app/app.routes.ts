/**
 * Created by rjaf on 30/05/2017.
 */

import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {HomeComponent} from './components/home/home.component';
import {LoginGuardService} from './services/login-guard.service';
import {DashboardComponent} from './components/dashboard/dashboard.component';
import {DefaultComponent} from './components/dashboard/default/default.component';
import {ClassComponent} from './components/dashboard/class/class.component';
import {ScheduleComponent} from './components/dashboard/schedule/schedule.component';
import {GroupCreateComponent} from './components/dashboard/class/groups/group-create/group-create.component';
import {ResultsComponent} from './components/dashboard/results/results.component';
import {ExamCreateComponent} from './components/dashboard/class/exams/exam-create/exam-create.component';
import {GroupViewComponent} from './components/dashboard/class/groups/group-view/group-view.component';
import {QuestionsComponent} from './components/dashboard/class/questions/questions.component';
import {QuestionCreateComponent} from './components/dashboard/class/questions/question-create/question-create.component';
import {ExamsComponent} from 'app/components/dashboard/class/exams/exams.component';
import {NotificationComponent} from './components/notification/notification.component';
import {ExamSubmissionComponent} from './components/dashboard/class/exams/exam-submission/exam-submission.component';
import {ExamResultComponent} from './components/dashboard/class/exams/exam-result/exam-result.component';
import {ExamResultAllComponent} from './components/dashboard/class/exams/exam-result-all/exam-result-all.component';
import {ProfileComponent} from './components/profile/profile.component';
import {GroupScoreComponent} from './components/dashboard/class/groups/group-score/group-score.component';

export const routes: Routes = [
  { path: '', component: HomeComponent , pathMatch: 'full', canActivate: [LoginGuardService]   },
  { path: 'login', component: LoginComponent, canActivate: [LoginGuardService] },
  { path: 'register', component: RegisterComponent, canActivate: [LoginGuardService] },
  { path: 'dashboard', component: DashboardComponent,
    children: [
      { path: '', component: DefaultComponent },
      { path: 'classes/:class_id/groups/:group_id/exams/new', component: ExamCreateComponent },
      { path: 'classes/:class_id/groups/:group_id/exams/:exam_id', component: ExamsComponent },
      { path: 'classes/:class_id/groups/:group_id/exams/:exam_id/submit', component: ExamSubmissionComponent },
      { path: 'classes/:class_id/groups/:group_id/exams/:exam_id/submission/:submission_id', component: ExamResultComponent },
      { path: 'classes/:class_id/groups/:group_id/exams/:exam_id/results', component: ExamResultAllComponent },
      { path: 'classes/:class_id/groups/:group_id/results', component: GroupScoreComponent },
      { path: 'classes/:class_id/questions/new', component: QuestionCreateComponent},
      { path: 'classes/:class_id/questions', component: QuestionsComponent},
      { path: 'classes/:class_id', component: ClassComponent,
        children: [
          { path: 'groups/new', component: GroupCreateComponent},
          { path: 'groups/:group_id', component: GroupViewComponent},
        ]},

      { path: 'schedule', component: ScheduleComponent },
      { path: 'results', component: ResultsComponent },
      { path: 'notifications', component: NotificationComponent },
      { path: 'profile', component: ProfileComponent}
    ]
  }

];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);
