//
//  ViewQuestionnaire.m
//  Permis Bateau
//
//  Created by Alexandre Dubois on 16/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import "ViewQuestionnaire.h"
#import "Question.h"


@interface ViewQuestionnaire ()

@end

int numeroQuestionEnCour;
NSTimer *timer;

@implementation ViewQuestionnaire
//@synthesize managedObjectContext;

- (void)viewDidLoad {
    [super viewDidLoad];
    
    
    self.barTimerQuestion.progress = 0.0;
    numeroQuestionEnCour = 0;
    if(self.theme != nil){
        if(self.examenThematique){
            [self executionExamenThematique];
        }else{
            
        }
        
        /*NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
         NSEntityDescription *entity = [NSEntityDescription entityForName:@"Question" inManagedObjectContext:context];
         [fetchRequest setEntity:entity];
         NSError *error;
         questionnaire.listeQuestion = [context executeFetchRequest:fetchRequest error:&error];*/
        
    }
}
/**
 * Si on est dans un examen thematique, execute la simulation.
 */
-(void) executionExamenThematique{
    self.questionnaire = [[Questionnaire alloc]init];;
    
    
    id delegate = [[UIApplication sharedApplication] delegate];
    self.managedObjectContext = [delegate managedObjectContext];
    
    
    
    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
    
    NSEntityDescription *entity = [NSEntityDescription
                                   entityForName:@"Question" inManagedObjectContext:self.managedObjectContext];
    
    
    NSSortDescriptor *sortDescriptor = [[NSSortDescriptor alloc] initWithKey:@"numero" ascending:YES];
    
    NSPredicate *predicate = [NSPredicate predicateWithFormat: @"ANY theme.numero = %@",self.theme.numero];
    
    [fetchRequest setEntity:entity];
    [fetchRequest setSortDescriptors:@[sortDescriptor]];
    [fetchRequest setPredicate:predicate];
    
    NSError *error;
    self.questionnaire.listeQuestion = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
    
    for(Question *tempQues in self.questionnaire.listeQuestion){
        if(tempQues.enoncer != nil)
            NSLog([NSString stringWithFormat:tempQues.enoncer]);
    }
    
    [self questionSuivante];
    
}


- (void) questionSuivante{
    [self incrementationNombreQuestion];
    self.barTimerQuestion.progress = 0.0;
    //[self startProgressTapped];
    //[self performSelectorOnMainThread:@selector(makeMyProgressBarMoving) withObject:nil waitUntilDone:NO];
    [self casovacTimer];
}


-(void)casovacTimer{
    self.barTimerQuestion.progress = 0;
    self.cas = 0;
    self.casovacCas = [NSTimer scheduledTimerWithTimeInterval: 1
                                                       target: self
                                                     selector: @selector(casovacAction)
                                                     userInfo: nil
                                                      repeats: YES];
    
    
}

/*-(void)enregistreReponse{
    if (self.cas <= 30) {
        [self.barTimerQuestion setProgress:self.barTimerQuestion.progress + 0.033];
        NSLog(@"%f",self.barTimerQuestion.progress);
    } else {
        NSLog(@"Temps limite depassé question suivante !");
        [self.casovacCas invalidate];
    }
    self.cas = self.cas +1;
}*/

-(void)casovacAction{
    if (self.cas <= 30) {
        [self.barTimerQuestion setProgress:self.barTimerQuestion.progress + 0.033];
        NSLog(@"%f",self.barTimerQuestion.progress);
    } else {
        NSLog(@"Temps limite depassé question suivante !");
        [self.casovacCas invalidate];
    }
    self.cas = self.cas +1;
}


- (void) incrementationNombreQuestion {
    numeroQuestionEnCour++;
    [self.labelNombreQuestion setText:[NSString stringWithFormat:@"%d/%lu", numeroQuestionEnCour,(unsigned long)self.questionnaire.listeQuestion.count]];
}



- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
