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
#import "ViewListeCorrection.h"
#import <QuartzCore/QuartzCore.h>

@interface ViewQuestionnaire ()

@end

int numeroQuestionEnCour;
Question *questionEnCour;
NSTimer *timer;

@implementation ViewQuestionnaire
//@synthesize managedObjectContext;

- (void)viewDidLoad {
    [super viewDidLoad];
    
    UIBarButtonItem *newBackButton =
    [[UIBarButtonItem alloc] init];
    
    newBackButton.title =@"Arrêter examen";
    self.navigation.backBarButtonItem = newBackButton;
    
    //self.navigation.hidesBackButton =YES;
    //    [[self navigationItem] setBackBarButtonItem:newBackButton];
    
    self.boutonValiderQuestion.layer.cornerRadius = 8;
    self.boutonValiderQuestion.layer.borderWidth = 1.0f;
    self.boutonValiderQuestion.layer.borderColor = [[UIColor blackColor] CGColor];
    [self.boutonValiderQuestion setBackgroundColor:[UIColor colorWithRed:25/255.0f green:255/255.0f blue:67/255.0f alpha:0.5f]];
    self.boutonCorrection.layer.cornerRadius = 8;
    self.boutonCorrection.layer.borderWidth = 1.0f;
    self.boutonCorrection.layer.borderColor = [[UIColor blackColor] CGColor];
    [self.boutonCorrection setBackgroundColor:[UIColor colorWithRed:25/255.0f green:255/255.0f blue:67/255.0f alpha:0.5f]];

    

    
    self.barTimerQuestion.progress = 0.0;
    numeroQuestionEnCour = 0;
    if(self.serie != nil){
       // if(self.examenThematique){
            [self executionExamenThematique];
        //}else{
            
       // }
        
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
    
    self.boutonCorrection.hidden = YES;
    
    self.questionnaire = [[Questionnaire alloc]init];;
    
    if(self.examenThematique){
        [self.barTimerQuestion setHidden:YES];
    }else{
        
    }
    
    id delegate = [[UIApplication sharedApplication] delegate];
    self.managedObjectContext = [delegate managedObjectContext];
    
    
    
    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
    
    NSEntityDescription *entity = [NSEntityDescription
                                   entityForName:@"Question" inManagedObjectContext:self.managedObjectContext];
    
    
    NSSortDescriptor *sortDescriptor = [[NSSortDescriptor alloc] initWithKey:@"numero" ascending:YES];
    
    NSPredicate *predicate = [NSPredicate predicateWithFormat: @"ANY serie.id = %@",self.serie.id];
    
    [fetchRequest setPredicate:predicate];
    [fetchRequest setEntity:entity];
    [fetchRequest setSortDescriptors:@[sortDescriptor]];
    
    NSError *error;
    self.questionnaire.listeQuestion = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
    self.questionnaire.listeReponse = [NSMutableArray array];
    
    /*for(Question *tempQues in self.questionnaire.listeQuestion){
        if(tempQues.enoncer != nil)
            NSLog([NSString stringWithFormat:tempQues.enoncer]);
    }*/

    if(self.questionnaire.listeQuestion.count != 0){
        [self questionSuivante];
        [self initialisationScreenForQuestion];
    }else{
        [self.enoncerQuestion setText:@"Erreur de chargement de la question cliquez sur retour"];
        self.boutonValiderQuestion.hidden = YES;
        self.boutonCorrection.hidden = YES;
    }
}


-(void) initialisationScreenForQuestion{
    
    questionEnCour = self.questionnaire.listeQuestion[numeroQuestionEnCour-1];
    
    NSString *nomQuestion = questionEnCour.image;

    //UIImage * result = [UIImage imageWithContentsOfFile:[NSString stringWithFormat:@"%@/%@.%@", @"/Users/alexandredubois/workspace_C/Objective-C/Permis_Bateau_Projet_Annuel/Permis Bateau Iphone/Permis Bateau/Images.xcassets", @"1", @".jpeg"]];
    NSString * documentsDirectoryPath = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0];  
    UIImage *imageQuestion = [self loadImage:nomQuestion ofType:@"png" inDirectory:documentsDirectoryPath];
    
    if(imageQuestion==nil){
        nomQuestion = [nomQuestion stringByAppendingString:@".png"];
        imageQuestion = [UIImage imageNamed:nomQuestion];
    }

    
    if(questionEnCour.image != nil){
        self.imageQuestion.image = imageQuestion;
    }
    
    if(questionEnCour.enoncer != nil){
        [self.enoncerQuestion setText:questionEnCour.enoncer];
            [self.enoncerQuestion setFont: [UIFont fontWithName:@"HelveticaNeue" size:16.0]];
        }else{
        [self.enoncerQuestion setHidden:NO];
    }


    if(questionEnCour.reponseA != nil){
        [self.texteReponseA setText:questionEnCour.reponseA];
        self.boutonASelect = false;
        self.boutonReponseA.layer.cornerRadius = 8;
        self.boutonReponseA.layer.borderWidth = 1.0f;
        self.boutonReponseA.layer.borderColor = [[UIColor blackColor] CGColor];
        [self.boutonReponseA setBackgroundColor:[UIColor colorWithRed:255/255.0f green:255/255.0f blue:255/255.0f alpha:0.5f]];
        [self.texteReponseA setTextColor:[UIColor colorWithRed:0/255.0f green:0/255.0f blue:0/255.0f alpha:1.0f]];
        [self.texteReponseA setHidden:NO];
        [self.boutonReponseA setHidden:NO];
    }else{
        [self.texteReponseA setHidden:YES];
        [self.boutonReponseA setHidden:YES];
        self.boutonASelect = false;
    }

    if(questionEnCour.reponseB != nil){
        [self.texteReponseB setText:questionEnCour.reponseB];
        self.boutonBSelect = false;
        self.boutonReponseB.layer.cornerRadius = 8;
        self.boutonReponseB.layer.borderWidth = 1.0f;
        self.boutonReponseB.layer.borderColor = [[UIColor blackColor] CGColor];
        [self.boutonReponseB setBackgroundColor:[UIColor colorWithRed:255/255.0f green:255/255.0f blue:255/255.0f alpha:0.5f]];
        [self.texteReponseB setTextColor:[UIColor colorWithRed:0/255.0f green:0/255.0f blue:0/255.0f alpha:1.0f]];
        [self.texteReponseB setHidden:NO];
        [self.boutonReponseB setHidden:NO];
    }else{
        [self.texteReponseB setHidden:YES];
        [self.boutonReponseB setHidden:YES];
        self.boutonBSelect = false;
    }

    if(questionEnCour.reponseC != nil){
        [self.texteReponseC setText:questionEnCour.reponseC];
        self.boutonCSelect = false;
        self.boutonReponseC.layer.cornerRadius = 8;
        self.boutonReponseC.layer.borderWidth = 1.0f;
        self.boutonReponseC.layer.borderColor = [[UIColor blackColor] CGColor];
        [self.boutonReponseC setBackgroundColor:[UIColor colorWithRed:255/255.0f green:255/255.0f blue:255/255.0f alpha:0.5f]];
        [self.texteReponseC setTextColor:[UIColor colorWithRed:0/255.0f green:0/255.0f blue:0/255.0f alpha:1.0f]];
        [self.texteReponseC setHidden:NO];
        [self.boutonReponseC setHidden:NO];
    }else{
        [self.texteReponseC setHidden:YES];
        [self.boutonReponseC setHidden:YES];
        self.boutonCSelect = false;
    }

    if(questionEnCour.reponseD != nil){
        [self.texteReponseD setText:questionEnCour.reponseD];
         self.boutonDSelect = false;
        self.boutonReponseD.layer.cornerRadius = 8;
        self.boutonReponseD.layer.borderWidth = 1.0f;
        self.boutonReponseD.layer.borderColor = [[UIColor blackColor] CGColor];
        [self.boutonReponseD setBackgroundColor:[UIColor colorWithRed:255/255.0f green:255/255.0f blue:255/255.0f alpha:0.5f]];
        [self.texteReponseD setTextColor:[UIColor colorWithRed:0/255.0f green:0/255.0f blue:0/255.0f alpha:1.0f]];
        [self.texteReponseD setHidden:NO];
        [self.boutonReponseD setHidden:NO];
    }else{
        [self.texteReponseD setHidden:YES];
        [self.boutonReponseD setHidden:YES];
        self.boutonDSelect = false;
    }

}

- (IBAction)boutonValiderQuestion:(id)sender {
    if(!self.examenThematique){
        if ([self.casovacCas isValid]) {
            [self.casovacCas invalidate];
            
        }
        //self.casovacCas = nil;
    }
    [self enregistrerReponseQuestion];
    [self questionSuivante];
    
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
            self.boutonValiderQuestion.hidden = YES;
            self.boutonCorrection.hidden = NO;
        }
        [self initialisationScreenForQuestion];
    }else{
        if(numeroQuestionEnCour>=self.questionnaire.listeQuestion.count){
            self.boutonValiderQuestion.hidden = YES;
            self.boutonCorrection.hidden = NO;
        }
        self.barTimerQuestion.progress = 0.0;
        //[self startProgressTapped];
        //[self performSelectorOnMainThread:@selector(makeMyProgressBarMoving) withObject:nil waitUntilDone:NO];
        [self casovacTimer];
        [self initialisationScreenForQuestion];
    }
}


-(void)casovacTimer{
    self.barTimerQuestion.progress = 0;
    self.cas = 0;
    self.casovacCas = [NSTimer scheduledTimerWithTimeInterval: 1.0f
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
        //[self.casovacCas invalidate];
        if(numeroQuestionEnCour>=self.questionnaire.listeQuestion.count){
                 NSLog(@"Temps limite depassé Fin questionnaire !");
//            [self.casovacCas invalidate];
            //self.casovacCas = nil;
            /*if ([self.casovacCas isValid]) {
                [self.casovacCas invalidate];

            }
            self.casovacCas = nil;
            */
            [self enregistrerReponseQuestion];

        }else{
            NSLog(@"Temps limite depassé question suivante !");
            [self enregistrerReponseQuestionTempsDepasse];
            [self questionSuivante];
            [self initialisationScreenForQuestion];
        }
    }
    self.cas = self.cas +1;
}

-(void)enregistrerReponseQuestionTempsDepasse{
    Reponse *reponse = [[Reponse alloc] init];
    

        reponse.reponseA = false;
        reponse.reponseB = false;
        reponse.reponseC = false;
        reponse.reponseD = false;

        reponse.idQuestion = questionEnCour.numero;
    
    
    [self.questionnaire.listeReponse addObject:reponse];
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
//    [self.labelNombreQuestion setText:[NSString stringWithFormat:@"%d/%lu", numeroQuestionEnCour,(unsigned long)self.questionnaire.listeQuestion.count]];
    self.navigation.title = [NSString stringWithFormat:@"%d/%lu", numeroQuestionEnCour,(unsigned long)self.questionnaire.listeQuestion.count];
}



- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)pushBoutonReponseA:(id)sender {
    if(self.boutonASelect){
        self.boutonASelect = false;
        [self.boutonReponseA setBackgroundColor:[UIColor colorWithRed:255/255.0f green:255/255.0f blue:255/255.0f alpha:0.5f]];
        [self.texteReponseA setTextColor:[UIColor colorWithRed:0/255.0f green:0/255.0f blue:0/255.0f alpha:1.0f]];
    }else{
        self.boutonASelect = true;
        [self.boutonReponseA setBackgroundColor:[UIColor colorWithRed:15/255.0f green:163/255.0f blue:255/255.0f alpha:0.5f]];
        [self.texteReponseA setTextColor:[UIColor colorWithRed:100/255.0f green:163/255.0f blue:255/255.0f alpha:1.0f]];
    }
}

- (IBAction)pushBoutonReponseB:(id)sender {
    if(self.boutonBSelect){
        self.boutonBSelect = false;
        [self.boutonReponseB setBackgroundColor:[UIColor colorWithRed:255/255.0f green:255/255.0f blue:255/255.0f alpha:0.5f]];
        [self.texteReponseB setTextColor:[UIColor colorWithRed:0/255.0f green:0/255.0f blue:0/255.0f alpha:1.0f]];
    }else{
        self.boutonBSelect = true;
        [self.boutonReponseB setBackgroundColor:[UIColor colorWithRed:15/255.0f green:163/255.0f blue:255/255.0f alpha:0.5f]];
        [self.texteReponseB setTextColor:[UIColor colorWithRed:100/255.0f green:163/255.0f blue:255/255.0f alpha:1.0f]];
    }
}

- (IBAction)pushBoutonReponseC:(id)sender {
    if(self.boutonCSelect){
        self.boutonCSelect = false;
        [self.boutonReponseC setBackgroundColor:[UIColor colorWithRed:255/255.0f green:255/255.0f blue:255/255.0f alpha:0.5f]];
        [self.texteReponseC setTextColor:[UIColor colorWithRed:0/255.0f green:0/255.0f blue:0/255.0f alpha:1.0f]];
    }else{
        self.boutonCSelect = true;
        [self.boutonReponseC setBackgroundColor:[UIColor colorWithRed:15/255.0f green:163/255.0f blue:255/255.0f alpha:0.5f]];
        [self.texteReponseC setTextColor:[UIColor colorWithRed:100/255.0f green:163/255.0f blue:255/255.0f alpha:1.0f]];
    }
}

- (IBAction)pushBoutonReponseD:(id)sender {
    if(self.boutonDSelect){
        self.boutonDSelect = false;
        [self.boutonReponseD setBackgroundColor:[UIColor colorWithRed:255/255.0f green:255/255.0f blue:255/255.0f alpha:0.5f]];
        [self.texteReponseD setTextColor:[UIColor colorWithRed:0/255.0f green:0/255.0f blue:0/255.0f alpha:1.0f]];
    }else{
        self.boutonDSelect = true;
        [self.boutonReponseD setBackgroundColor:[UIColor colorWithRed:15/255.0f green:163/255.0f blue:255/255.0f alpha:0.5f]];
        [self.texteReponseD setTextColor:[UIColor colorWithRed:100/255.0f green:163/255.0f blue:255/255.0f alpha:1.0f]];
    }
}


- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
        [self enregistrerReponseQuestion];
        ViewListeCorrection *viewListeCorrection = (ViewListeCorrection *)[segue destinationViewController];
        viewListeCorrection.questionnaire = self.questionnaire;
        viewListeCorrection.serieEnCours = self.serie;
        viewListeCorrection.examenThematique = self.examenThematique;
}


- (IBAction)pushBoutonCorrection:(id)sender {
    
    if(!self.examenThematique){
        if ([self.casovacCas isValid]) {
            [self.casovacCas invalidate];
        }
        self.casovacCas = nil;
    }
}

-(UIImage *) loadImage:(NSString *)fileName ofType:(NSString *)extension inDirectory:(NSString *)directoryPath {
    UIImage * result = [UIImage imageWithContentsOfFile:[NSString stringWithFormat:@"%@/%@.%@", directoryPath, fileName, extension]];
    
    return result;
}
@end
