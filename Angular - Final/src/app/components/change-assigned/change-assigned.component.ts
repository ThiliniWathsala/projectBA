import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EmpType } from 'src/app/modelClasses/emp-type';
import { DataSharingService } from 'src/app/services/data-sharing.service';
import { GetEmployees } from 'src/app/modelClasses/get-employees';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { EncrDecrService } from 'src/app/services/encr-decr.service';
import { Project } from 'src/app/modelClasses/project';

@Component({
  selector: 'app-change-assigned',
  templateUrl: './change-assigned.component.html',
  styleUrls: ['./change-assigned.component.css']
})

export class ChangeAssignedComponent implements OnInit {

  constructor(private http1: HttpClient,
    private router: Router,
    private cookieService: CookieService,
    private EncrDecr: EncrDecrService) { }

    sbalist: string[] = [];
    devlist: string[] = [];
    jbalist: string[] = [];
  
    jbalist2: string[] = [];
    jbalist1: string[] = [];
    devlist1: string[] = [];
    devlist2: string[] = [];
    devlist3: string[] = [];
    devlist4: string[] = [];
    devlist5: string[] = [];

  newproject: Project = {
    projectid: '',
    name: '',
    pm: '',
    lba: '',
    customerid: '',
    jba1: '',
    jba2: '',
    dev1: '',
    dev2: null,
    dev3: null,
    dev4: null,
    dev5: null,
    description: '',
    status:'',
    lastupdated:'',
    days:''
  };

  statusproject: Project = {
    projectid: '',
    name: '',
    pm: '',
    lba: '',
    customerid: '',
    jba1: '',
    jba2: '',
    dev1: '',
    dev2: null,
    dev3: null,
    dev4: null,
    dev5: null,
    description: '',
    status:'',
    lastupdated:'',
    days:''
  };

  admincheck = false;

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
      else if (this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted3)) != "PM")
        this.router.navigate(['../UserAccessOnly']);
      else
      {
        this.getEmpList();
      }
    }
    else
      this.router.navigate(['../Login']);

  }

  getEmpList() {
    var encrypted = this.EncrDecr.set('123456$#@$^@1ERF', 'projectid');
    this.newproject.projectid = this.EncrDecr.get('123456$#@$^@1ERF', this.cookieService.get(encrypted));
    console.log(this.newproject.projectid);

    this.http1.post<string[]>('http://localhost:8080/project/getchangeavailablesba', this.newproject).subscribe(
      res => {
        this.sbalist = res;
        // console.log(this.sbalist);
        this.newproject.lba = this.sbalist[0];
      });

      this.http1.post<string[]>('http://localhost:8080/project/getchangeavailablejba1', this.newproject).subscribe(
      res => {
        this.jbalist1 = res;
        // console.log(this.jbalist1);
        this.newproject.jba1 = this.jbalist1[0];
        this.jbalist = this.jbalist1;

        this.http1.post<string[]>('http://localhost:8080/project/getchangeavailablejba2', this.newproject).subscribe(
      res => {
        this.jbalist2 = res;
        // console.log(this.jbalist2);
        this.newproject.jba2 = this.jbalist2[0];

        this.http1.post<string[]>('http://localhost:8080/project/getchangeavailabledev1', this.newproject).subscribe(
      res => {
        this.devlist1 = res;
        console.log(this.devlist1);
        if(this.devlist1[0]!=null)
          this.newproject.dev1 = this.devlist1[0];
        this.devlist = this.devlist1;

        this.http1.post<string[]>('http://localhost:8080/project/getchangeavailabledev2', this.newproject).subscribe(
      res => {
        this.devlist2 = res;
        // console.log(this.devlist2);
        if(this.devlist2[0]!=null)
          this.newproject.dev2 = this.devlist2[0];

        this.http1.post<string[]>('http://localhost:8080/project/getchangeavailabledev3', this.newproject).subscribe(
      res => {
        this.devlist3 = res;
        // console.log(this.devlist3);
        if(this.devlist3[0]!=null)
          this.newproject.dev3 = this.devlist3[0];

        this.http1.post<string[]>('http://localhost:8080/project/getchangeavailabledev4', this.newproject).subscribe(
      res => {
        this.devlist4 = res;
        // console.log(this.devlist4);
        if(this.devlist4[0]!=null)
          this.newproject.dev4 = this.devlist4[0];

        this.http1.post<string[]>('http://localhost:8080/project/getchangeavailabledev5', this.newproject).subscribe(
      res => {
        this.devlist5 = res;
        console.log(this.devlist5);
        if(this.devlist5[0]!=null)
          this.newproject.dev5 = this.devlist5[0];

        this.http1.post<Project>('http://localhost:8080/project/projectinfo', this.newproject).subscribe(
      res => {
        this.statusproject = res;
        console.log(this.statusproject);
        if(this.statusproject.status==='completed')
          this.admincheck = true;

        

        this.changeBA1();
        this.changeBA2();
        this.changeDev1();
        // if(this.newproject.dev2!=null)
          this.changeDev2();
        // if(this.newproject.dev3!=null)
          this.changeDev3();
        // if(this.newproject.dev4!=null)
          this.changeDev4();
        // if(this.newproject.dev5!=null)
          this.changeDev5();
          // console.log(this.devlist2);
          
      }

    );
      });
      });
      });
      });
      });
      });

      
    
      

      

      

      

      
      });
    // this.http1.post<string[]>('http://localhost:8080/project/getavailablejba', this.newproject).subscribe(
    //   res => {
    //     this.jbalist = res;
    //     console.log(this.jbalist);
    //     this.http1.post<string[]>('http://localhost:8080/project/getchangeavailablesba', this.newproject).subscribe(
    //   res => {
    //     this.sbalist = res;
    //     console.log(this.sbalist);
    //   }

    // );
    // this.http1.post<string[]>('http://localhost:8080/project/getavailabledev', this.newproject).subscribe(
    //   res => {
    //     this.devlist = res;
    //     console.log(this.devlist);
    //   }

    // );

    

    //   }
    // );    
  }

  onSubmit() {
    var encrypt0 = this.EncrDecr.set('123456$#@$^@1ERF', 'projectid');
    this.newproject.projectid = this.EncrDecr.get('123456$#@$^@1ERF',this.cookieService.get(encrypt0));
    console.log(this.newproject);
    this.http1.post<Project>('http://localhost:8080/project/changeassigned', this.newproject).subscribe(
      res => {
        this.newproject = res;
        console.log(this.newproject);
        alert('Saved Changes');
        this.router.navigate(['../ProjectInfo']);
      }

    );
  } 

  changeBA1()
  {
      this.jbalist1 = [];

      this.jbalist.forEach(element => {
        if(element!=this.newproject.jba2)
          this.jbalist1.push(element);
      });

      // this.changeBA2();
  }

  changeBA2()
  {
    this.jbalist2 = [];
      
      this.jbalist.forEach(element => {
        if(element!=this.newproject.jba1)
          this.jbalist2.push(element);
      });
  }

  changeDev11()
  {
    this.changeDev2();
    this.changeDev3();
    this.changeDev4();
    this.changeDev5();
  }

  changeDev22()
  {
    this.changeDev1();
    this.changeDev3();
    this.changeDev4();
    this.changeDev5();
  }

  changeDev33()
  {
    this.changeDev2();
    this.changeDev1();
    this.changeDev4();
    this.changeDev5();
  }

  changeDev44()
  {
    this.changeDev2();
    this.changeDev3();
    this.changeDev1();
    this.changeDev5();
  }

  changeDev55()
  {
    this.changeDev2();
    this.changeDev3();
    this.changeDev4();
    this.changeDev1();
  }

  changeDev1()
  {
    this.devlist1 = [];
      
      this.devlist.forEach(element => {
        if(element!=this.newproject.dev2 && element!=this.newproject.dev3 && element!=this.newproject.dev4 && element!=this.newproject.dev5)
          this.devlist1.push(element);
      });
  }

  changeDev2()
  {
    this.devlist2 = [];
      
      this.devlist.forEach(element => {
        if(element!=this.newproject.dev1 && element!=this.newproject.dev3 && element!=this.newproject.dev4 && element!=this.newproject.dev5)
          this.devlist2.push(element);
      });
  }

  changeDev3()
  {
    this.devlist3 = [];
      
      this.devlist.forEach(element => {
        if(element!=this.newproject.dev2 && element!=this.newproject.dev1 && element!=this.newproject.dev4 && element!=this.newproject.dev5)
          this.devlist3.push(element);
      });
  }

  changeDev4()
  {
    this.devlist4 = [];
      
      this.devlist.forEach(element => {
        if(element!=this.newproject.dev2 && element!=this.newproject.dev3 && element!=this.newproject.dev1 && element!=this.newproject.dev5)
          this.devlist4.push(element);
      });
  }

  changeDev5()
  {
    this.devlist5 = [];
      
      this.devlist.forEach(element => {
        if(element!=this.newproject.dev2 && element!=this.newproject.dev3 && element!=this.newproject.dev4 && element!=this.newproject.dev1)
          this.devlist5.push(element);
      });
  }

}




