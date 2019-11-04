import { Component, OnInit } from '@angular/core';
import { EmpType } from 'src/app/modelClasses/emp-type';
import { CookieService } from 'ngx-cookie-service';
import { Calender } from 'src/app/modelClasses/calender';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { EncrDecrService } from 'src/app/services/encr-decr.service';

@Component({
  selector: 'app-leavesubmit',
  templateUrl: './leavesubmit.component.html',
  styleUrls: ['./leavesubmit.component.css']
})
export class LeavesubmitComponent implements OnInit {

  constructor(private cookieService: CookieService,
    private http: HttpClient,
    private router: Router,
    private EncrDecr: EncrDecrService) { }

  pmlist: string[] = [];

  model: EmpType = {
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

  modelcalender: Calender = {
    leaveid: '',
    pm: '',
    ba: '',
    startdate: null,
    enddate: null,
    reason: '',
    status: '',
    date: ''
  }

  admincheck = false;
  isActive = false;
  min1date:string;
  min2date:string;

  ngOnInit() {
    var encrypted = this.EncrDecr.set('123456$#@$^@1ERF', 'empid');
    var encrypted1 = this.EncrDecr.set('123456$#@$^@1ERF', 'passstatus');
    var encrypted2 = this.EncrDecr.set('123456$#@$^@1ERF', 'gradestatus');
    var encrypted3 = this.EncrDecr.set('123456$#@$^@1ERF', 'emptype');

    if (this.cookieService.check(encrypted)) {
      if (this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted1)) === "new")
        this.router.navigate(['../NewPassword']);
      else if (this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted2)) === "required")
        this.router.navigate(['../GradeAlert']);
      else if (this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted3)) === "admin" || this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted3)) === "PM")
        this.router.navigate(['../UserAccessOnly']);
      else
        this.getData();
    }
    else
      this.router.navigate(['../Login']);
  }

  setMin1Date()
  {
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();
    
    this.min1date = yyyy + '-' + mm + '-' + dd;
    this.min2date = yyyy + '-' + mm + '-' + dd;
    // document.write(today);
    
  }

  setMin2Date()
  {
      this.min2date = this.modelcalender.startdate;
  }

  public getData() {
    var encrypt = this.EncrDecr.set('123456$#@$^@1ERF', 'empname');
    this.model.fname = this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypt));
    encrypt = this.EncrDecr.set('123456$#@$^@1ERF', 'empid');
    this.model.employeeid = this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypt));
    console.log("Get PM List");
    // this.http.post<string[]>('http://localhost:8080/employee/pmlist', this.model).subscribe(
    //   response => {
    //     this.pmlist = response;
    //     console.log(this.pmlist);
        
    //     console.log(this.min1date);
        
    //   }
    // );
    this.setMin1Date();
  }

  onSubmit(): void {

    var encrypted = this.EncrDecr.set('123456$#@$^@1ERF', 'empid');
    this.modelcalender.ba = this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted));

    console.log(this.modelcalender);

    this.http.post<Calender>('http://localhost:8080/employee/submitleave', this.modelcalender).subscribe(
      res => {
        console.log(this.modelcalender);
        this.modelcalender = res;
        console.log(this.modelcalender);
      }
    );
    alert('Leave Note was Submitted');
    window.location.reload();
  }

}
