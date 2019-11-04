import { Component, OnInit } from '@angular/core';
import { EmpType } from 'src/app/modelClasses/emp-type';
import { Project } from 'src/app/modelClasses/project';
import { Srs } from 'src/app/modelClasses/srs';
import { CookieService } from 'ngx-cookie-service';
import { HttpClient } from '@angular/common/http';
import { EncrDecrService } from 'src/app/services/encr-decr.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-transferproject',
  templateUrl: './transferproject.component.html',
  styleUrls: ['./transferproject.component.css']
})
export class TransferprojectComponent implements OnInit {

  projectlist: Project[] = [];

  pro: Project; 

  modelproject: Project = {
    projectid:'',
    name:'',
    pm:'',
    lba:'',
    customerid:'',
    jba1:'',
    jba2:'',
    dev1:'',
    dev2:'',
    dev3:'',
    dev4:'',
    dev5:'',
    description:'',
    status:'',
    lastupdated:'',
    days:''  
    };

  pm: EmpType = {
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

  admincheck = false;

  other:boolean = false;
  jba:boolean = false;

  constructor(private cookieService: CookieService,
    private https: HttpClient,
    private EncrDecr: EncrDecrService,
    private router: Router) { }

  ngOnInit() {

    var encrypted0 = this.EncrDecr.set('123456$#@$^@1ERF', 'adminaccess');
    if (this.cookieService.check(encrypted0))
      this.admincheck = true;

    var encrypted = this.EncrDecr.set('123456$#@$^@1ERF', 'empid');
    var encrypted1 = this.EncrDecr.set('123456$#@$^@1ERF', 'passstatus');
    var encrypted2 = this.EncrDecr.set('123456$#@$^@1ERF', 'gradestatus');
    var encrypted3 = this.EncrDecr.set('123456$#@$^@1ERF', 'emptype');

    if (this.cookieService.check(encrypted)) {
      if (this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted1)) === "new")
        this.router.navigate(['../NewPassword']);
      else if (this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted2)) === "required")
        this.router.navigate(['../GradeAlert']);
      else if (this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted3)) != "admin")
        this.router.navigate(['../AccessRestricted']);
      else
      {
        this.getProjects();
        if (this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted3)) != "PM")
          this.other = true;
        if (this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted3)) === "JBA")
          this.jba = true;
      }
        
    }
    else
      this.router.navigate(['../Login']);

  }

  public getProjects() {
    console.log("Starting ProjectList");
    this.https.post<Project[]>('http://localhost:8080/project/currentprojects', null).subscribe(
      response => {
        this.projectlist = response;
        console.log(this.projectlist);
      }
    );
    }

  submit($value)
  {
    if(this.email($value.username))
    {
    this.pm.email = $value.username;
    this.pm.status = $value.projectid;
    this.https.post<EmpType>('http://localhost:8080/project/changepm', this.pm).subscribe(
      response => {
        this.pm = response;
        console.log(this.pm);
        if(this.pm.status==='invalid')
          alert('No PM Found. Please check the email address provided');
        else{
          alert('Project Transfer Successful');
          this.getProjects();
        }
      }
    );  
    }
    else
      alert('Invalid Email');
  }

  email($value) {
    return /\S+@\S+\.\S+/.test($value)
  }
}
