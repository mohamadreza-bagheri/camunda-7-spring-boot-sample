import { Component, Input, Output, EventEmitter } from '@angular/core';
import { ContractService } from 'src/app/services/contract.service';

@Component({
  selector: 'app-task-form',
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.scss']
})
export class TaskFormComponent {
  @Input() task: any;
  @Input() contract!: any;
  @Input() userName!: string;
  @Output() taskCompleted = new EventEmitter<void>();

  action!: string;
  description!: string;
  submitting = false;

  constructor(
    private camundaService: ContractService
  ) { }

   submitDecision(action: string) {
    this.submitting = true;

    const model = {
      userName: this.userName,
      taskId: this.task.id,
      description: this.description
    }
    this.camundaService.approve(model, action, this.contract.id ?? '0').subscribe({
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
