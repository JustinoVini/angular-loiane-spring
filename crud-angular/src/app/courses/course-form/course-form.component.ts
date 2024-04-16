import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NonNullableFormBuilder } from '@angular/forms';
import { CoursesService } from '../service/courses.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Location } from '@angular/common';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss']
})
export class CourseFormComponent implements OnInit {

  form = this.formBuilder.group({
    name: new FormControl('', { nonNullable: true }),
    category: ['']
  });

  constructor(private formBuilder: NonNullableFormBuilder,
    private _courseService: CoursesService,
    private snackBar: MatSnackBar,
    private location: Location) {

  }

  ngOnInit(): void {

  }

  onSubmit() {
    this._courseService.save(this.form.value)
      .subscribe(response => this.onSuccess(), error => this.onError());
  }

  onCancel() {
    this.location.back();
  }

  private onSuccess() {
    this.snackBar.open("Curso salvo com sucesso!", '', { duration: 5000 });
    this.location.back();
  }

  private onError() {
    this.snackBar.open("Erro ao salvar curso.", '', { duration: 5000 });
  }

}
