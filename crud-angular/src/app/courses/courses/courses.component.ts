import { Component, OnInit } from '@angular/core';
import { Course } from '../model/course';
import { CoursesService } from '../service/courses.service';
import { Observable, catchError, of } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from 'src/app/shared/components/error-dialog/error-dialog.component';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss']
})
export class CoursesComponent implements OnInit {

  courses$: Observable<Course[]>;

  constructor(private _courseService: CoursesService, public dialog: MatDialog, private router: Router, private route: ActivatedRoute) {
    this.courses$ = this._courseService.list()
      .pipe(
        catchError(error => {
          this.onError('Erro ao carregar cursos.');
          return of([])
        })
      );
  }

  ngOnInit(): void {

  }

  onAdd() {
    this.router.navigate(['new'], { relativeTo: this.route }); /* pega a rota que está e passa só o new */
  }

  onError(errorMessage: string) {
    this.dialog.open(ErrorDialogComponent, {
      data: errorMessage
    });
  }

}
