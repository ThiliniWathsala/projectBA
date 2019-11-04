import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { EncrDecrService } from 'src/app/services/encr-decr.service';
import { Calender } from 'src/app/modelClasses/calender';
import { getLocaleDayNames } from '@angular/common';
import { Project } from 'src/app/modelClasses/project';
import { EmpType } from 'src/app/modelClasses/emp-type';

@Component({
  selector: 'app-cancelleave',
  templateUrl: './cancelleave.component.html',
  styleUrls: ['./cancelleave.component.css']
})
export class CancelleaveComponent implements OnInit {

  dayslist:Calender[] = [];

  emp: EmpType = {
    employeeid: '',
    fname: '',
    lname: '',
    password: '',
    type: '',
    email: '',
    username: '',
    contact1: '',
    contact2: '',
    addressno: '',
    street: '',
    city: '',
    status: '',
    about: '',
    image: '',
    passstatus: '',
    gradestatus: ''
  }

  day: Calender = {
    leaveid:'',
    pm:'',
    ba:'',
    date:'',
    startdate:'',
    enddate:'',
    reason:'',
    status:'2',
  };

  empname:string;

  admincheck = false;

  constructor(private http1: HttpClient,
    private router: Router,
    private cookieService: CookieService,
    private EncrDecr: EncrDecrService) { }


  ngOnInit() {
    var encrypted1 = this.EncrDecr.set('123456$#@$^@1ERF', 'adminaccess');
  if (this.cookieService.check(encrypted1))
    this.admincheck = true;

  var encrypted = this.EncrDecr.set('123456$#@$^@1ERF', 'empid');
  encrypted1 = this.EncrDecr.set('123456$#@$^@1ERF', 'passstatus');
  var encrypted2 = this.EncrDecr.set('123456$#@$^@1ERF', 'gradestatus');
  var encrypted3 = this.EncrDecr.set('123456$#@$^@1ERF', 'emptype');

  if (this.cookieService.check(encrypted)) {
    if (this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted1)) === "new")
      this.router.navigate(['../NewPassword']);
    else if (this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted2)) === "required")
      this.router.navigate(['../GradeAlert']);
    else if (this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted3)) == "Dev" || this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted3)) == "PM" || this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted3)) == "admin")
      this.router.navigate(['../UserAccessOnly']);
      else
      this.getDays();
  }
  else
    this.router.navigate(['../Login']);
    
  }

  getDays()
  {
    var encrypted = this.EncrDecr.set('123456$#@$^@1ERF', 'empid');
    this.emp.employeeid = this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted));
    this.day.ba = this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted));
    encrypted = this.EncrDecr.set('123456$#@$^@1ERF', 'empname');
    this.empname = this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted));

    this.http1.post<Calender[]>('http://localhost:8080/employee/leavedays', this.emp).subscribe(
      res => {
        this.dayslist = res;
        console.log(this.dayslist);
      });   
  }

  onSubmit()
  {
    this.http1.post<null>('http://localhost:8080/employee/leavecancel', this.day).subscribe(
      res => {
        alert('Cancelled Successfully');
        this.getDays();
      });  
  }

}
