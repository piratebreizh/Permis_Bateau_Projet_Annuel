//
//  ViewQuestionnaire.h
//  Permis Bateau
//
//  Created by Alexandre Dubois on 16/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Theme.h"
#import "Questionnaire.h"
#import "Serie.h"

@interface ViewQuestionnaire : UIViewController

@property (weak, nonatomic) IBOutlet UILabel *labelNombreQuestion;
@property (weak, nonatomic) IBOutlet UIProgressView *barTimerQuestion;



//Theme récupéré de ViewListeDeroulanteExamenThematique lors du clique sur un des thème
@property (strong, nonatomic) Theme *theme;

@property (strong, nonatomic) Serie *serie;

@property Questionnaire *questionnaire;
@property (nonatomic, assign) BOOL examenThematique;


@property (strong, nonatomic) NSFetchedResultsController *fetchedResultsController;
@property (nonatomic,strong) NSManagedObjectContext* managedObjectContext;

- (IBAction)pushBoutonReponseA:(id)sender;
- (IBAction)pushBoutonReponseB:(id)sender;
- (IBAction)pushBoutonReponseC:(id)sender;
- (IBAction)pushBoutonReponseD:(id)sender;


@property (weak, nonatomic) IBOutlet UIButton *boutonReponseA;
@property (weak, nonatomic) IBOutlet UIButton *boutonReponseB;
@property (weak, nonatomic) IBOutlet UIButton *boutonReponseC;
@property (weak, nonatomic) IBOutlet UIButton *boutonReponseD;

@property (nonatomic, assign) BOOL boutonASelect;
@property (nonatomic, assign) BOOL boutonBSelect;
@property (nonatomic, assign) BOOL boutonCSelect;
@property (nonatomic, assign) BOOL boutonDSelect;

@property (nonatomic, assign) float cas;
@property (strong, nonatomic) NSTimer *casovacCas;


@property (weak, nonatomic) IBOutlet UIImageView *imageQuestion;
@property (weak, nonatomic) IBOutlet UILabel *enoncerQuestion;
@property (weak, nonatomic) IBOutlet UILabel *texteReponseA;
@property (weak, nonatomic) IBOutlet UILabel *texteReponseB;
@property (weak, nonatomic) IBOutlet UILabel *texteReponseC;
@property (weak, nonatomic) IBOutlet UILabel *texteReponseD;



@end

