import { Component, Input, Output, EventEmitter } from '@angular/core';
import { PaymentService } from 'src/app/services/payment.service';

@Component({
  selector: 'app-task-form',
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.scss']
})
export class TaskFormComponent {
  @Input() task: any;
  @Input() payment!: any;
  @Input() userName!: string;
  @Output() taskCompleted = new EventEmitter<void>();

  action!: string;
  description!: string;
  submitting = false;

  constructor(
    private service: PaymentService
  ) { }

   submitDecision(action: string) {
    this.submitting = true;

    const model = {
      processInstanceId: this.task.processInstanceId,
      userName: this.userName,
      taskId: this.task.id,
      description: this.description
    }
    this.service.approve(model, action, this.payment.id ?? '0').subscribe({
      next: value => {
        this.submitting = false;
        this.taskCompleted.emit();
        alert('***** completed successfully! *****')
      },
      error: err => {
        this.submitting = false;
        alert(JSON.stringify(err));
      }
    });

  }
}
