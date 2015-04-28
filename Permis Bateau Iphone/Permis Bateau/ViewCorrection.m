//
//  ViewController.m
//  Permis Bateau
//
//  Created by Alexandre Dubois on 15/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import "ViewCorrection.h"
#import "Question.h"
#import "Reponse.h"
#import "ViewCorrectionDetail.h"

@interface ViewCorrection ()

@property (weak, nonatomic) IBOutlet UILabel *labelNote;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection1;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection2;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection3;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection4;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection5;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection6;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection7;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection8;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection9;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection10;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection11;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection12;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection13;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection14;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection15;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection16;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection17;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection18;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection19;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection20;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection21;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection22;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection23;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection24;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection25;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection26;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection27;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection28;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection29;
@property (weak, nonatomic) IBOutlet UIButton *boutonCorrection30;

@property int nombreBonneReponse;


@end



@implementation ViewCorrection




- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationItem.hidesBackButton = YES;
    [self creationListeBouton];
    [self masqueQuestionsNonTraiteesAfficheCorrection];
    [self initLabelNote];
    [self enregistrerTestPourStatistique];
}

-(void) initLabelNote{
    [self.labelNote setText:[NSString stringWithFormat:@"Résulat : %d/%tu", _nombreBonneReponse, self.questionnaire.listeQuestion.count]];
}

-(void) creationListeBouton{
    self.listeBouton = [NSMutableArray array];
    [self.listeBouton addObject:_boutonCorrection1];
    [self.listeBouton addObject:_boutonCorrection2];
    [self.listeBouton addObject:_boutonCorrection3];
    [self.listeBouton addObject:_boutonCorrection4];
    [self.listeBouton addObject:_boutonCorrection5];
    [self.listeBouton addObject:_boutonCorrection6];
    [self.listeBouton addObject:_boutonCorrection7];
    [self.listeBouton addObject:_boutonCorrection8];
    [self.listeBouton addObject:_boutonCorrection9];
    [self.listeBouton addObject:_boutonCorrection10];
    [self.listeBouton addObject:_boutonCorrection11];
    [self.listeBouton addObject:_boutonCorrection12];
    [self.listeBouton addObject:_boutonCorrection13];
    [self.listeBouton addObject:_boutonCorrection14];
    [self.listeBouton addObject:_boutonCorrection15];
    [self.listeBouton addObject:_boutonCorrection16];
    [self.listeBouton addObject:_boutonCorrection17];
    [self.listeBouton addObject:_boutonCorrection18];
    [self.listeBouton addObject:_boutonCorrection19];
    [self.listeBouton addObject:_boutonCorrection20];
    [self.listeBouton addObject:_boutonCorrection21];
    [self.listeBouton addObject:_boutonCorrection22];
    [self.listeBouton addObject:_boutonCorrection23];
    [self.listeBouton addObject:_boutonCorrection24];
    [self.listeBouton addObject:_boutonCorrection25];
    [self.listeBouton addObject:_boutonCorrection26];
    [self.listeBouton addObject:_boutonCorrection27];
    [self.listeBouton addObject:_boutonCorrection28];
    [self.listeBouton addObject:_boutonCorrection29];
    [self.listeBouton addObject:_boutonCorrection30];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}



-(void) creationVuePrincipal{
    
}


-(void) masqueQuestionsNonTraiteesAfficheCorrection{
    

    for ( UIButton *boutonTemp in self.listeBouton )
    {
        boutonTemp.hidden = YES;
    }

    int idx = 0;
    _nombreBonneReponse=0;
    for (Question *questionTemp in self.questionnaire.listeQuestion)
    {
        if(questionTemp != nil){
            UIButton *boutonTemp = [self.listeBouton objectAtIndex:idx];
            boutonTemp.hidden = NO;
            
            Reponse *reponse = [self.questionnaire.listeReponse objectAtIndex:idx];

            BOOL reponseCorrect = YES;
            
            if(([questionTemp.reponseCorrectA boolValue] && !reponse.reponseA) || (![questionTemp.reponseCorrectA boolValue] && reponse.reponseA)){
                reponseCorrect = NO;
            }
            if(([questionTemp.reponseCorrectB boolValue] && !reponse.reponseB) || (![questionTemp.reponseCorrectB boolValue] && reponse.reponseB)){
                reponseCorrect = NO;
            }
            if(([questionTemp.reponseCorrectC boolValue] && !reponse.reponseC) || (![questionTemp.reponseCorrectC boolValue] && reponse.reponseC)){
                reponseCorrect = NO;
            }
            if(([questionTemp.reponseCorrectD boolValue] && !reponse.reponseD) || (![questionTemp.reponseCorrectD boolValue] && reponse.reponseD)){
                reponseCorrect = NO;
            }
            
            if(!reponseCorrect){
                [boutonTemp setBackgroundColor:[UIColor redColor]];
                boutonTemp.layer.cornerRadius = 8;
            }else{
                [boutonTemp setBackgroundColor:[UIColor greenColor]];
                boutonTemp.layer.cornerRadius = 8;
                _nombreBonneReponse =+1;
                
            }
        }
        idx =+1;
    }
}



- (IBAction)pushBoutonDetail:(id)sender {

}


- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {

    if ([sender isMemberOfClass:[UIButton class]])
    {
        UIButton *btn = (UIButton *)sender;
        if(![btn.currentTitle isEqualToString:@"Menu principal"]){
            Question *question;
            Reponse *reponse;
        
            question = [self.questionnaire.listeQuestion objectAtIndex:[btn.currentTitle intValue]-1];
            reponse = [self.questionnaire.listeReponse objectAtIndex:[btn.currentTitle intValue]-1];
        
            ViewCorrectionDetail *viewCorrectionDetail = (ViewCorrectionDetail *)[segue destinationViewController];
            viewCorrectionDetail.question = question;
            viewCorrectionDetail.reponse = reponse;
        }
    }
}


- (void)enregistrerTestPourStatistique{
    
    /*Cours *theme = [NSEntityDescription
                    insertNewObjectForEntityForName:@"Theme"
                    inManagedObjectContext:context];*/


}


@end
