import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { EncrDecrService } from 'src/app/services/encr-decr.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private cookieService:CookieService,
              private router:Router,
              private EncrDecr:EncrDecrService) { }

  ngOnInit() {

          this.cookieService.deleteAll();
        // this.router.navigate(['../Login']);
  }

}
