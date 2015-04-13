//
//  ViewQuestionnaire.m
//  Permis Bateau
//
//  Created by Alexandre Dubois on 16/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import "ViewQuestionnaire.h"
#import "Question.h"
#import "Reponse.h"


@interface ViewQuestionnaire ()

@end

int numeroQuestionEnCour;
Question *questionEnCour;
NSTimer *timer;

@implementation ViewQuestionnaire
//@synthesize managedObjectContext;

- (void)viewDidLoad {
    [super viewDidLoad];

    
    self.barTimerQuestion.progress = 0.0;
    numeroQuestionEnCour = 0;
    if(self.serie != nil){
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
    
    [self.barTimerQuestion setHidden:YES];
    
    id delegate = [[UIApplication sharedApplication] delegate];
    self.managedObjectContext = [delegate managedObjectContext];
    
    
    
    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
    
    NSEntityDescription *entity = [NSEntityDescription
                                   entityForName:@"Question" inManagedObjectContext:self.managedObjectContext];
    
    
    NSSortDescriptor *sortDescriptor = [[NSSortDescriptor alloc] initWithKey:@"numero" ascending:YES];
    
    NSPredicate *predicate = [NSPredicate predicateWithFormat: @"ANY serie.numero = %@",self.serie.numero];
    
    [fetchRequest setEntity:entity];
    [fetchRequest setSortDescriptors:@[sortDescriptor]];
    [fetchRequest setPredicate:predicate];
    
    NSError *error;
    self.questionnaire.listeQuestion = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
    self.questionnaire.listeReponse = [NSMutableArray array];
    
    /*for(Question *tempQues in self.questionnaire.listeQuestion){
        if(tempQues.enoncer != nil)
            NSLog([NSString stringWithFormat:tempQues.enoncer]);
    }*/

    [self questionSuivante];
    [self initialisationScreenForQuestion];
}


-(void) initialisationScreenForQuestion{
    
    questionEnCour = self.questionnaire.listeQuestion[numeroQuestionEnCour-1];
    
    
    if(questionEnCour.image != nil){
        self.imageQuestion.image =[UIImage imageNamed:questionEnCour.image];
    }
    
    if(questionEnCour.enoncer != nil){
       [self.enoncerQuestion setText:questionEnCour.enoncer];
        
        // Une solution pas trop mal mais c'est pas exactement ça
//       [self.enoncerQuestion setAdjustsFontSizeToFitWidth:YES];
      
        
    
//        self.enoncerQuestion.text = [addresslist valueForKey:@"public"];
        NSString *message = questionEnCour.enoncer;
        CGSize maximumLabelSize = CGSizeMake(280,1000);
        
        // use font information from the UILabel to calculate the size
        CGSize expectedLabelSize = [message sizeWithFont:self.enoncerQuestion.font constrainedToSize:maximumLabelSize lineBreakMode:NSLineBreakByWordWrapping];
        
        // create a frame that is filled with the UILabel frame data
        CGRect newFrame = self.enoncerQuestion.frame;
        
        // resizing the frame to calculated size
        newFrame.size.height = expectedLabelSize.height;
        
        // put calculated frame into UILabel frame
        self.enoncerQuestion.frame = newFrame;
    }else{
        [self.enoncerQuestion setHidden:NO];
    }

    
    
    if(questionEnCour.reponseA != nil && questionEnCour.reponseCorrectA != nil){
        [self.texteReponseA setText:questionEnCour.reponseA];
        self.boutonASelect = false;
        [self.boutonReponseA setTitle:@"F" forState:UIControlStateNormal];
    }else{
        [self.texteReponseA setHidden:YES];
        [self.boutonReponseA setHidden:YES];
    }

    if(questionEnCour.reponseB != nil && questionEnCour.reponseCorrectB != nil){
        [self.texteReponseB setText:questionEnCour.reponseB];
         self.boutonBSelect = false;
        [self.boutonReponseB setTitle:@"F" forState:UIControlStateNormal];

    }else{
        [self.texteReponseB setHidden:YES];
        [self.boutonReponseB setHidden:YES];
    }

    if(questionEnCour.reponseC != nil && questionEnCour.reponseCorrectC != nil){
        [self.texteReponseC setText:questionEnCour.reponseC];
        self.boutonCSelect = false;
        [self.boutonReponseC setTitle:@"F" forState:UIControlStateNormal];

    }else{
        [self.texteReponseC setHidden:YES];
        [self.boutonReponseC setHidden:YES];
    }

    if(questionEnCour.reponseD != nil && questionEnCour.reponseCorrectD != nil){
        [self.texteReponseD setText:questionEnCour.reponseD];
         self.boutonDSelect = false;
        [self.boutonReponseD setTitle:@"F" forState:UIControlStateNormal];

    }else{
        [self.texteReponseD setHidden:YES];
        [self.boutonReponseD setHidden:YES];
    }

}

- (IBAction)boutonValiderQuestion:(id)sender {
    [self enregistrerReponseQuestion];
    [self questionSuivante];
    [self initialisationScreenForQuestion];
}

-(void) viewWillDisappear:(BOOL)animated {
    if ([self.navigationController.viewControllers indexOfObject:self]==NSNotFound) {
       [self.casovacCas invalidate];
        self.casovacCas = nil;
    }
    [super viewWillDisappear:animated];
}




- (void) questionSuivante{
    [self incrementationNombreQuestion];
    if(self.examenThematique){
        if(numeroQuestionEnCour>=self.questionnaire.listeQuestion.count){
            
        }
    }else{
        self.barTimerQuestion.progress = 0.0;
        //[self startProgressTapped];
        //[self performSelectorOnMainThread:@selector(makeMyProgressBarMoving) withObject:nil waitUntilDone:NO];
        [self casovacTimer];
    }
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
        [self.casovacCas invalidate];
        if(numeroQuestionEnCour>=self.questionnaire.listeQuestion.count){
            self.casovacCas = nil;
            [self enregistrerReponseQuestion];
        }else{
            NSLog(@"Temps limite depassé question suivante !");
            [self questionSuivante];
            [self initialisationScreenForQuestion];
            [self enregistrerReponseQuestion];
        }
    }
    self.cas = self.cas +1;
}


-(void)enregistrerReponseQuestion{
    Reponse *reponse = [[Reponse alloc] init];
    
    if(self.boutonASelect){
        reponse.reponseA = true;
    }
    if(self.boutonBSelect){
        reponse.reponseB = true;
    }
    if(self.boutonCSelect){
        reponse.reponseC = true;
    }
    if(self.boutonDSelect){
        reponse.reponseD = true;
    }
    
    if(questionEnCour.numero != nil){
        reponse.idQuestion = questionEnCour.numero;
    }
    
    
    [self.questionnaire.listeReponse addObject:reponse];
    
}

- (void) incrementationNombreQuestion {
    numeroQuestionEnCour++;
    [self.labelNombreQuestion setText:[NSString stringWithFormat:@"%d/%lu", numeroQuestionEnCour,(unsigned long)self.questionnaire.listeQuestion.count]];
}



- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)pushBoutonReponseA:(id)sender {
    if(self.boutonASelect){
        self.boutonASelect = false;
        [self.boutonReponseA setTitle:@"F" forState:UIControlStateNormal];
    }else{
        self.boutonASelect = true;
        [self.boutonReponseA setTitle:@"V" forState:UIControlStateNormal];
    }
}

- (IBAction)pushBoutonReponseB:(id)sender {
    if(self.boutonBSelect){
        self.boutonBSelect = false;
        [self.boutonReponseB setTitle:@"F" forState:UIControlStateNormal];
    }else{
        self.boutonBSelect = true;
        [self.boutonReponseB setTitle:@"V" forState:UIControlStateNormal];
    }
}

- (IBAction)pushBoutonReponseC:(id)sender {
    if(self.boutonCSelect){
        self.boutonCSelect = false;
        [self.boutonReponseC setTitle:@"F" forState:UIControlStateNormal];
    }else{
        self.boutonCSelect = true;
        [self.boutonReponseC setTitle:@"V" forState:UIControlStateNormal];
    }
}

- (IBAction)pushBoutonReponseD:(id)sender {
    if(self.boutonDSelect){
        self.boutonDSelect = false;
        [self.boutonReponseD setTitle:@"F" forState:UIControlStateNormal];
    }else{
        self.boutonDSelect = true;
        [self.boutonReponseD setTitle:@"V" forState:UIControlStateNormal];
    }
}
@end
