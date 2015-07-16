//
//  ViewController.m
//  Permis Bateau
//
//  Created by Alexandre Dubois on 15/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import "ViewMAJ.h"
#import "Reachability.h"
#import "Theme.h"
#import "Question.h"
#import "Serie.h"
#import "Cours.h"
#import "Configuration.h"




@interface ViewMAJ ()
@property (weak, nonatomic) IBOutlet UILabel *labelMAJ;
@property (weak, nonatomic) IBOutlet UIActivityIndicatorView *indicateurChargement;

@property NSMutableArray *listeImagesDownload;
@property NSMutableArray *listeImageDelete;

@property NSMutableArray *listePDFDownload;
@property NSMutableArray *listePDFDelete;


@property NSMutableData * pData;

@property Configuration *lastDateMaj;

@property BOOL autorisationDownload;

@property NSURLConnection * pConnection;

@end



@implementation ViewMAJ
//@synthesize managedObjectContext;

- (void)viewDidLoad {
    [super viewDidLoad];

    id delegate = [[UIApplication sharedApplication] delegate];
    self.managedObjectContext = [delegate managedObjectContext];
    
    //DESIGN
    [self.view setBackgroundColor:[UIColor colorWithRed:100/255.0f green:163/255.0f blue:255/255.0f alpha:1.0f]];
    self.indicateurChargement.transform = CGAffineTransformMakeScale(0.75, 0.75);
    [self.labelMAJ setText:@"Téléchargement en cours"];
    [self.labelMAJ setTintColor:[UIColor colorWithRed:245.0/255.0 green:245.0/255.0 blue:245.0/255.0 alpha:1.0]];
    [self.labelMAJ setTextColor:[UIColor whiteColor]];
    [self.labelMAJ setFont:[UIFont fontWithName:@"HelveticaNeue-Bold" size:23.0]];
    
    
    [self saveFirstLunch];
    [self declenchementWebService];
}

- (UIImage *)imageWithImage:(UIImage *)image scaledToSize:(CGSize)newSize {
    //UIGraphicsBeginImageContext(newSize);
    // In next line, pass 0.0 to use the current device's pixel scaling factor (and thus account for Retina resolution).
    // Pass 1.0 to force exact pixel size.
    UIGraphicsBeginImageContextWithOptions(newSize, NO, 0.0);
    [image drawInRect:CGRectMake(0, 0, newSize.width, newSize.height)];
    UIImage *newImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return newImage;
}

-(void) goMenu{
    [self.indicateurChargement stopAnimating];
    [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
    UIStoryboard *sb = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
    UIViewController *vc = [sb instantiateViewControllerWithIdentifier:@"ViewMenu"];
    vc.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
    [self presentViewController:vc animated:YES completion:NULL];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void) saveFirstLunch{

    NSError *error;
    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
    [fetchRequest setEntity:[NSEntityDescription
                             entityForName:@"Configuration" inManagedObjectContext:self.managedObjectContext]];
    // ID = 1 on impose le faite que le numéro de la MAJ soit 1
    [fetchRequest setPredicate:[NSPredicate predicateWithFormat: @"id = 1"]];
    [fetchRequest setFetchLimit:1];
    NSArray *listeConfiguration = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
    Configuration *applicationJamaisLancee = [listeConfiguration firstObject];
    
    if(applicationJamaisLancee==nil){
        NSString *filePath = [[NSBundle mainBundle] pathForResource:@"json_init" ofType:@"json"];
        if ([[NSFileManager defaultManager] fileExistsAtPath:filePath]){
            _pData = [NSMutableData dataWithData:[NSData dataWithContentsOfFile:filePath]];
            [self enregistrerJsonDansBDD:NO];
        }
    }
}

-(void)declenchementPopUpWebService{
    //This goes in the implementation (.m) file
    UIAlertView *message = [[UIAlertView alloc] initWithTitle:@"Information"
                                                      message:@"Des nouvelles questions sont disponibles gratuitement souhaitez-vous les télécharger ?"
                                                     delegate:self
                                            cancelButtonTitle:@"Non"
                                            otherButtonTitles:@"Oui", nil, nil];
    [message show];
}

- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex{
    // The first button (or "cancel" button)
    if (buttonIndex == 0)
    {
        [self goMenu];
        _autorisationDownload =NO;
    }
    // The second button on the alert view
    if (buttonIndex == 1)
    {
        _autorisationDownload =YES;
        [self enregistrerJsonDansBDD:YES];
        [self enregistrerImages2];
        [self enregistrerPDF];
    }
    [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
    [self goMenu];
}

-(void) declenchementWebService{
    
    //Date de la dernière MAJ et construction du l'URL
    NSError *error;
    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
    [fetchRequest setEntity:[NSEntityDescription
                             entityForName:@"Configuration" inManagedObjectContext:self.managedObjectContext]];
    // ID = 1 on impose le faite que le numéro de la MAJ soit 1
    [fetchRequest setPredicate:[NSPredicate predicateWithFormat: @"id = 1"]];
    [fetchRequest setFetchLimit:1];
    NSArray *listeConfiguration = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
    self.lastDateMaj = [listeConfiguration firstObject];

    NSString *date_String;
    
    
    // Si aucune configuration, contenu la dernière date de MAJ n'est présent dans la BDD alors on en créée une
    if(self.lastDateMaj!=nil){
        date_String = self.lastDateMaj.valeur;
    }


    NSString *stringURL = @"http://cap-horn.osmose-hebergement.com/ws/getmaj?date=";
    stringURL = [stringURL stringByAppendingString:date_String];
    
    // 1. NSURL
    NSURL * pURL = [NSURL URLWithString:stringURL];
  
    
    if ([NSData dataWithContentsOfURL:pURL]){
        NSLog(@"Device is connected to the internet");
        
        // 2. NSURLRequest
        NSURLRequest * pRequest = [NSURLRequest requestWithURL:pURL];
        
        // 3. NSURLConnection
        _pConnection = [[NSURLConnection alloc] initWithRequest:pRequest delegate:self];
        
        [_pConnection start];
        // Loading Show Alert View...
        [UIApplication sharedApplication].networkActivityIndicatorVisible = YES;
        
        [self.indicateurChargement startAnimating];
        
    }else{
        NSLog(@"Device is NOT connected to the internet");
        [self goMenu];
    }
}


#pragma mark NSURLConnectionDelegate
- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error {
    NSLog(@"Erreur -> %@", error);
}

//
- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response {
    _pData = [[NSMutableData alloc]init];
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data {
    if( _pData != nil )
        [_pData appendData:data];
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    
    Reachability *networkReachability = [Reachability reachabilityForInternetConnection];
    
    NetworkStatus networkStatus = [networkReachability currentReachabilityStatus];
    if (networkStatus == NotReachable) {
        NSLog(@"Pas de connection internet");
    } else {
        if( connection != nil ){
            if( _pData ) {
                NSError * pError = nil;
                NSDictionary * pDict = [NSJSONSerialization JSONObjectWithData:_pData options:0 error:&pError];
                if( [NSJSONSerialization isValidJSONObject:pDict] ) {
                    NSNumber * miseAjour = [pDict objectForKey:@"is_empty"];
                    if([miseAjour isEqualToNumber:[NSNumber numberWithBool:NO]]){
                        [self declenchementPopUpWebService];
                    }else if ([miseAjour isEqualToNumber:[NSNumber numberWithBool:YES]]){
                        [self goMenu];
                    }
                }
            }
            
        }
    }
}

- (void) enregistrerJsonDansBDD:(BOOL)modeWeb{
    
    NSError *error;
    
    
    if( _pData ) {
        NSError * pError = nil;
        
        NSDictionary * pDict = [NSJSONSerialization JSONObjectWithData:_pData options:0 error:&pError];
        BOOL modifDate = NO;
        if( [NSJSONSerialization isValidJSONObject:pDict] ) {
            
            // Création des thèmes
            NSArray * listeThemeCreation = [pDict objectForKey:@"themes_nouveaux"];
            
            for (NSDictionary *themeTemp in listeThemeCreation ){
                NSNumber * idTheme = [themeTemp objectForKey:@"id_theme"];
                NSString* nom = [themeTemp  objectForKey:@"nom"];
                NSNumber* numero = [themeTemp  objectForKey:@"numero"];
                if(idTheme != nil && nom != nil && numero != nil){
                    Theme *nouveauTheme = [NSEntityDescription
                                           insertNewObjectForEntityForName:@"Theme"
                                           inManagedObjectContext:self.managedObjectContext];
                    nouveauTheme.numero = numero;
                    nouveauTheme.nom = nom;
                    nouveauTheme.id = idTheme;
                }else{
                    NSLog(@"%@",@"Le thème n'a pas été créé");
                }
                
                modifDate=YES;
            }
            
            
            //Modification des thèmes
            NSArray * listeThemeModification = [pDict objectForKey:@"themes_modifies"];
            
            for (NSDictionary *themeTemp in listeThemeModification ){
                modifDate=YES;
                NSNumber * idTheme = [themeTemp objectForKey:@"id_theme"];
                NSString* nom = [themeTemp  objectForKey:@"nom"];
                NSNumber* numero = [themeTemp  objectForKey:@"numero"];
                if(idTheme != nil && nom != nil && numero != nil){
                    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
                    
                    [fetchRequest setEntity:[NSEntityDescription
                                             entityForName:@"Theme" inManagedObjectContext:self.managedObjectContext]];
                    [fetchRequest setPredicate:[NSPredicate predicateWithFormat: @"id = %@",idTheme]];
                    [fetchRequest setFetchLimit:1];
                    NSArray *listeTheme = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
                    Theme *themeModif = [listeTheme firstObject];
                    
                    if(themeModif !=nil){
                        themeModif.numero = numero;
                        themeModif.nom = nom;
                    }else{
                        NSLog(@"Aucun thème présent en BDD impossible de le modifier");
                    }
                }else{
                    NSLog(@"Le thème n'a pas été modifié");
                }
            }
            
            //Suppression thème
            NSArray * listeThemeSupprimees = [pDict objectForKey:@"themes_supprimes"];
            
            for (NSNumber *idSerieTemp in listeThemeSupprimees){
                modifDate=YES;
                NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
                
                [fetchRequest setEntity:[NSEntityDescription
                                         entityForName:@"Theme" inManagedObjectContext:self.managedObjectContext]];
                [fetchRequest setPredicate:[NSPredicate predicateWithFormat: @"id = %@",idSerieTemp]];
                [fetchRequest setFetchLimit:1];
                NSArray *listeTheme = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
                Question *themeSupprimee = [listeTheme firstObject];
                if(themeSupprimee!=nil){
                    [self.managedObjectContext deleteObject:themeSupprimee];
                }
            }
            
            // Création des Series
            NSArray * listeSerie = [pDict objectForKey:@"examens_nouveaux"];
            
            for (NSDictionary *serieTemp in listeSerie ){
                modifDate=YES;
                NSNumber * idSerie  = [serieTemp objectForKey:@"id_examen"];
                NSNumber * idThemeSerie = [serieTemp objectForKey:@"id_theme"];
                NSString* nom = [serieTemp  objectForKey:@"nom"];
                NSNumber* numero = [serieTemp  objectForKey:@"numero"];
                
                if(idSerie!=nil && nom !=nil && numero !=nil ){
                    if(idThemeSerie !=nil){
                        if([idThemeSerie isEqualToNumber:[NSNumber numberWithInt:0]]){
                            Serie *serie = [NSEntityDescription
                                            insertNewObjectForEntityForName:@"Serie"
                                            inManagedObjectContext:self.managedObjectContext];
                            serie.numero = numero;
                            serie.nom = nom;
                            serie.theme = nil;
                            serie.id = idSerie;
                        }else{
                            NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
                            
                            [fetchRequest setEntity:[NSEntityDescription
                                                     entityForName:@"Theme" inManagedObjectContext:self.managedObjectContext]];
                            [fetchRequest setPredicate:[NSPredicate predicateWithFormat: @"id = %@",idThemeSerie]];
                            [fetchRequest setFetchLimit:1];
                            NSArray *listeTheme = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
                            Theme *themeSerie = [listeTheme firstObject];
                            
                            if(themeSerie!=nil){
                                Serie *serie = [NSEntityDescription
                                            insertNewObjectForEntityForName:@"Serie"
                                            inManagedObjectContext:self.managedObjectContext];
                                serie.numero = numero;
                                serie.nom = nom;
                                serie.theme = themeSerie;
                                NSLog(@"%@ %@",themeSerie.id,themeSerie.nom);
                                serie.id = idSerie;
                            }
                        }
                    }
                }
            }
            
            //Modification des Series
            NSArray * listeSerieModification = [pDict objectForKey:@"examens_modifies"];
            
            for (NSDictionary *serieTemp in listeSerieModification ){
                modifDate=YES;
                NSNumber * idExamen = [serieTemp objectForKey:@"id_examen"];
                NSNumber * idThemeExamen = [serieTemp objectForKey:@"id_theme"];
                NSString* nom = [serieTemp  objectForKey:@"nom"];
                NSNumber* numero = [serieTemp  objectForKey:@"numero"];
                
                
                if(idExamen!=nil && nom !=nil && numero !=nil && numero !=nil){
                    if(idThemeExamen !=nil){
                        NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
                        
                        [fetchRequest setEntity:[NSEntityDescription
                                                 entityForName:@"Serie" inManagedObjectContext:self.managedObjectContext]];
                        [fetchRequest setPredicate:[NSPredicate predicateWithFormat: @"id = %@",idExamen]];
                        [fetchRequest setFetchLimit:1];
                        NSArray *listeSerieModifier = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
                        Serie *serieModifier = [listeSerieModifier firstObject];
                        
                        if(serieModifier !=nil){
                            
                            NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
                            
                            [fetchRequest setEntity:[NSEntityDescription
                                                     entityForName:@"Theme" inManagedObjectContext:self.managedObjectContext]];
                            [fetchRequest setPredicate:[NSPredicate predicateWithFormat: @"id = %@",idThemeExamen]];
                            [fetchRequest setFetchLimit:1];
                            NSArray *listeTheme = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
                            Theme *themeSerie = [listeTheme firstObject];
                            if(themeSerie!=nil){
                                serieModifier.nom = nom;
                                serieModifier.theme = themeSerie;
                                serieModifier.numero = numero;
                            }
                        }
                        
                    }
                }
            }
            
            //Suppression Serie
            NSArray * listeSerieSupprimees = [pDict objectForKey:@"examens_supprimes"];
            
            for (NSNumber *idSerieTemp in listeSerieSupprimees ){
                modifDate=YES;
                NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
                
                [fetchRequest setEntity:[NSEntityDescription
                                         entityForName:@"Serie" inManagedObjectContext:self.managedObjectContext]];
                [fetchRequest setPredicate:[NSPredicate predicateWithFormat: @"id = %@",idSerieTemp]];
                [fetchRequest setFetchLimit:1];
                NSArray *listeSerie = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
                Question *serieSupprimee = [listeSerie firstObject];
                if(serieSupprimee!=nil){
                    [self.managedObjectContext deleteObject:serieSupprimee];
                }
            }

            
            // Création des Questions
            NSArray * listeQuestion = [pDict objectForKey:@"questions_nouvelles"];
            if([listeQuestion count] >0){
                self.listeImagesDownload = [[NSMutableArray alloc] init];
            }
            for (NSDictionary *questionTemp in listeQuestion ){
                modifDate=YES;
                NSNumber * idQuestion  = [questionTemp objectForKey:@"id_question"];
                NSNumber* numero = [questionTemp  objectForKey:@"numero_question"];
                NSString* enoncerQuestion = [NSString stringWithFormat: @"%@",[questionTemp  objectForKey:@"enonce_question"]];
                NSNumber * isCorrectA = [questionTemp objectForKey:@"is_correct_A"];
                NSNumber * isCorrectB = [questionTemp objectForKey:@"is_correct_B"];
                NSNumber * isCorrectC = [questionTemp objectForKey:@"is_correct_C"];
                NSNumber * isCorrectD = [questionTemp objectForKey:@"is_correct_D"];
                NSString* enoncerA = [NSString stringWithFormat: @"%@",[questionTemp  objectForKey:@"enonce_A"]];
                NSString* enoncerB = [NSString stringWithFormat: @"%@",[questionTemp  objectForKey:@"enonce_B"] ];
                NSString* enoncerC = [NSString stringWithFormat: @"%@",[questionTemp  objectForKey:@"enonce_C"] ];
                NSString* enoncerD = [NSString stringWithFormat: @"%@",[questionTemp  objectForKey:@"enonce_D"] ];
                NSString* idImage = [NSString stringWithFormat: @"%@", [questionTemp  objectForKey:@"id_image"]];
                NSNumber * idSerieQuestion = [questionTemp objectForKey:@"id_examen"];
                
                if(idQuestion!=nil && numero !=nil && enoncerQuestion !=nil && isCorrectA !=nil && isCorrectB != nil
                   && isCorrectC !=nil && isCorrectD !=nil && enoncerA !=nil && enoncerB !=nil && enoncerC !=nil && enoncerD !=nil
                   && idImage!=nil  &&  idSerieQuestion !=nil){
                    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
                    
                    [fetchRequest setEntity:[NSEntityDescription
                                             entityForName:@"Serie" inManagedObjectContext:self.managedObjectContext]];
                    [fetchRequest setPredicate:[NSPredicate predicateWithFormat: @"id = %@",idSerieQuestion]];
                    [fetchRequest setFetchLimit:1];
                    NSArray *listeSerie = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
                    Serie *serieQuestion = [listeSerie firstObject];
                    
                    if(serieQuestion !=nil){
                        Question *question = [NSEntityDescription
                                              insertNewObjectForEntityForName:@"Question"
                                              inManagedObjectContext:self.managedObjectContext];
                        question.id = idQuestion;
                        question.numero = numero;
                        question.enoncer = enoncerQuestion;
                        question.reponseCorrectA = isCorrectA;
                        question.reponseCorrectB = isCorrectB;
                        question.reponseCorrectC = isCorrectC;
                        question.reponseCorrectD = isCorrectD;
                        if(enoncerA.length!=0){
                            question.reponseA = enoncerA;
                        }
                        if(enoncerB.length!=0){
                            question.reponseB = enoncerB;
                        }
                        if(enoncerC.length!=0){
                            question.reponseC = enoncerC;
                        }
                        if(enoncerD.length!=0){
                            question.reponseD = enoncerD;
                        }
                        question.image = idImage;
                        question.serie = serieQuestion;
                        if(self.listeImagesDownload !=nil){
                            [self.listeImagesDownload addObject:question.image];
                        }
                    }
                }
            }
            
            // Modification des Questions
            NSArray * listeQuestionModifier = [pDict objectForKey:@"questions_modifiees"];
            if([listeQuestion count] >0){
                self.listeImagesDownload = [[NSMutableArray alloc] init];
            }
            for (NSDictionary *questionTemp in listeQuestionModifier ){
                modifDate=YES;
                NSNumber * idQuestion  = [questionTemp objectForKey:@"id_question"];
                NSNumber* numero = [questionTemp  objectForKey:@"numero_question"];
                NSString* enoncerQuestion = [questionTemp  objectForKey:@"enonce_question"];
                NSNumber * isCorrectA = [questionTemp objectForKey:@"is_correct_A"];
                NSNumber * isCorrectB = [questionTemp objectForKey:@"is_correct_B"];
                NSNumber * isCorrectC = [questionTemp objectForKey:@"is_correct_C"];
                NSNumber * isCorrectD = [questionTemp objectForKey:@"is_correct_D"];
                NSString* enoncerA = [NSString stringWithFormat: @"%@",[questionTemp  objectForKey:@"enonce_A"]];
                NSString* enoncerB = [NSString stringWithFormat: @"%@",[questionTemp  objectForKey:@"enonce_B"]];
                NSString* enoncerC = [NSString stringWithFormat: @"%@",[questionTemp  objectForKey:@"enonce_C"]];
                NSString* enoncerD = [NSString stringWithFormat: @"%@",[questionTemp  objectForKey:@"enonce_D"]];
                NSString* idImage = [NSString stringWithFormat: @"%@", [questionTemp  objectForKey:@"id_image"]];
                NSNumber * idSerieQuestion = [questionTemp objectForKey:@"id_examen"];
                
                
                if(idQuestion!=nil && numero !=nil && enoncerQuestion !=nil && isCorrectA !=nil && isCorrectB != nil
                   && isCorrectC !=nil && isCorrectD !=nil && enoncerA !=nil && enoncerB !=nil && enoncerC !=nil && enoncerD !=nil
                   && idImage!=nil  &&  idSerieQuestion !=nil){
                    
                    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
                    
                    [fetchRequest setEntity:[NSEntityDescription
                                             entityForName:@"Question" inManagedObjectContext:self.managedObjectContext]];
                    [fetchRequest setPredicate:[NSPredicate predicateWithFormat: @"id = %@",idQuestion]];
                    [fetchRequest setFetchLimit:1];
                    NSArray *listeQuestionModifier = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
                    Question *questionModifier = [listeQuestionModifier firstObject];
                    
                    if(questionModifier !=nil){
                        
                        NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
                        
                        [fetchRequest setEntity:[NSEntityDescription
                                                 entityForName:@"Serie" inManagedObjectContext:self.managedObjectContext]];
                        [fetchRequest setPredicate:[NSPredicate predicateWithFormat: @"id = %@",idSerieQuestion]];
                        [fetchRequest setFetchLimit:1];
                        NSArray *listeSerie = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
                        Serie *serieQuestion = [listeSerie firstObject];
                        
                        if(serieQuestion !=nil){
                            
                            questionModifier.numero = numero;
                            questionModifier.enoncer = enoncerQuestion;
                            questionModifier.reponseCorrectA = isCorrectA;
                            questionModifier.reponseCorrectB = isCorrectB;
                            questionModifier.reponseCorrectC = isCorrectC;
                            questionModifier.reponseCorrectD = isCorrectD;
                            if([enoncerA length] == 0){
                                questionModifier.reponseA= nil;
                            }else{
                                questionModifier.reponseA = enoncerA;
                            }
                            if([enoncerB length] == 0){
                                questionModifier.reponseB = nil;
                            }else{
                                questionModifier.reponseB = enoncerB;
                            }
                            if([enoncerC length] == 0){
                                questionModifier.reponseC = nil;
                            }else{
                                questionModifier.reponseC = enoncerC;
                            }
                            if([enoncerD length] == 0){
                                questionModifier.reponseD = nil;
                            }else{
                                questionModifier.reponseD = enoncerD;
                            }
                            questionModifier.image = idImage;
                            questionModifier.serie = serieQuestion;
                        }
                        if(self.listeImagesDownload !=nil){
                            [self.listeImagesDownload addObject:questionModifier.image];
                        }
                    }
                }
            }
            
            //Suppression Question
            NSArray * listeQuestionSupprimees = [pDict objectForKey:@"questions_supprimees"];

            if([listeQuestionSupprimees count]>0){
                self.listeImageDelete = [[NSMutableArray alloc] init];
            }
            for (NSNumber *idQuestionTemp in listeQuestionSupprimees ){
                modifDate=YES;
                NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
                
                [fetchRequest setEntity:[NSEntityDescription
                                         entityForName:@"Question" inManagedObjectContext:self.managedObjectContext]];
                [fetchRequest setPredicate:[NSPredicate predicateWithFormat: @"id = %@",idQuestionTemp]];
                [fetchRequest setFetchLimit:1];
                NSArray *listeQuestion = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
                Question *questionSupprimee = [listeQuestion firstObject];
                if(questionSupprimee!=nil){
                    [self.managedObjectContext deleteObject:questionSupprimee];
                    if(self.listeImageDelete != nil){
                        [self.listeImageDelete addObject:questionSupprimee.image];
                    }
                }
            }
            
            // Création des Cours
            NSArray * listeCours = [pDict objectForKey:@"cours_nouveaux"];
            if([listeCours count] >0){
                self.listePDFDownload = [[NSMutableArray alloc] init];
            }
            for (NSDictionary *coursTemp in listeCours){
                modifDate=YES;
                NSNumber* idCours  = [coursTemp objectForKey:@"id_cours"];
                NSString* nomCours = [coursTemp  objectForKey:@"nom"];
                NSString* nomPDF = [coursTemp  objectForKey:@"nom_pdf"];
                NSNumber* idTheme = [coursTemp objectForKey:@"id_theme"];
                
                if(idCours!=nil && nomCours !=nil && nomPDF !=nil && idTheme !=nil){
                    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
                    
                    [fetchRequest setEntity:[NSEntityDescription
                                             entityForName:@"Theme" inManagedObjectContext:self.managedObjectContext]];
                    [fetchRequest setPredicate:[NSPredicate predicateWithFormat: @"id = %@",idTheme]];
                    [fetchRequest setFetchLimit:1];
                    NSArray *listeTheme = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
                    Theme *themeCours = [listeTheme firstObject];
                    
                    if(themeCours !=nil){
                        Cours *cours = [NSEntityDescription
                                              insertNewObjectForEntityForName:@"Cours"
                                              inManagedObjectContext:self.managedObjectContext];
                        cours.id = idCours;
                        cours.nomCours = nomCours;
                        cours.nomPDF = nomPDF;
                        cours.theme = themeCours;
                        if(self.listePDFDownload !=nil){
                            [self.listePDFDownload addObject:idCours];
                        }
                    }
                }
            }
            
            //Suppression Cours
            NSArray * listeCoursSupprimes = [pDict objectForKey:@"cours_supprimer"];
            
            if([listeCoursSupprimes count]>0){
                self.listePDFDelete = [[NSMutableArray alloc] init];
            }
            for (NSNumber *idCours in listeCoursSupprimes){
                modifDate=YES;
                NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
                
                [fetchRequest setEntity:[NSEntityDescription
                                         entityForName:@"Cours" inManagedObjectContext:self.managedObjectContext]];
                [fetchRequest setPredicate:[NSPredicate predicateWithFormat: @"id = %@",idCours]];
                [fetchRequest setFetchLimit:1];
                NSArray *listeCours = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
                Cours *coursSupprimer = [listeCours firstObject];
                if(coursSupprimer!=nil){
                    [self.managedObjectContext deleteObject:coursSupprimer];
                    if(self.listeImageDelete != nil){
                        [self.listePDFDelete addObject:coursSupprimer.nomPDF];
                    }
                }
            }
            
            //Enregistrement de la Date de mise à jours
            NSString * dateUpdate = [NSString stringWithFormat: @"%@", [pDict objectForKey:@"date_update"]];
            dateUpdate = [dateUpdate stringByReplacingOccurrencesOfString:@" "
                                                 withString:@""];
            
            
            NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
            [fetchRequest setEntity:[NSEntityDescription
                                     entityForName:@"Configuration" inManagedObjectContext:self.managedObjectContext]];
            // ID = 1 on impose le faite que le numéro de la MAJ soit 1
            [fetchRequest setPredicate:[NSPredicate predicateWithFormat: @"id = 1"]];
            [fetchRequest setFetchLimit:1];
            NSArray *listeConfiguration = [self.managedObjectContext executeFetchRequest:fetchRequest error:&error];
            Configuration * dateMaj = [listeConfiguration firstObject];
            
            if(dateMaj==nil){
                dateMaj = [NSEntityDescription
                           insertNewObjectForEntityForName:@"Configuration"
                           inManagedObjectContext:self.managedObjectContext];
                dateMaj.id = [NSNumber numberWithInt:1];
                dateMaj.nom = @"Date de la dernière MAJ";
            } 
                dateMaj.valeur = dateUpdate;

            NSError *error;
            if (![self.managedObjectContext save:&error]) {
                NSLog(@"Erreur lors de l'enregistrement: %@", [error localizedDescription]);
            }
        }
    }
        
}

-(void) enregistrerImages2{

    //Definitions
    NSString * documentsDirectoryPath = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0];
    NSLog(@"Phase de téléchargement des images");

    CGRect screenRect = [[UIScreen mainScreen] bounds];
    CGFloat screenHeight = screenRect.size.height;
    
    NSString *sizeScreen;
    if(screenHeight==480){
        sizeScreen = @"HD";
    }else{
        sizeScreen = @"UHD";
    }
    
    for(NSString *tempImage in self.listeImagesDownload){
        
        //Construction de l'URL
        NSString *pathUrl = @"http://cap-horn.osmose-hebergement.com/ws/getImage?id=";
        pathUrl = [pathUrl stringByAppendingString:tempImage];
        pathUrl = [pathUrl stringByAppendingString:@"&resolution="];
        pathUrl = [pathUrl stringByAppendingString:sizeScreen];
        
        //Get Image From URL
        UIImage * imageFromURL = [self getImageFromURL:pathUrl];
        
        //Save Image to Directory
        [self saveImage:imageFromURL withFileName:tempImage ofType:@"png" inDirectory:documentsDirectoryPath];
 
    }
}


-(UIImage *) getImageFromURL:(NSString *)fileURL {
    UIImage * result;
    
    NSData * data = [NSData dataWithContentsOfURL:[NSURL URLWithString:fileURL]];
    result = [UIImage imageWithData:data];
    
    return result;
}

-(void) saveImage:(UIImage *)image withFileName:(NSString *)imageName ofType:(NSString *)extension inDirectory:(NSString *)directoryPath {
    if ([[extension lowercaseString] isEqualToString:@"png"]) {
        [UIImagePNGRepresentation(image) writeToFile:[directoryPath stringByAppendingPathComponent:[NSString stringWithFormat:@"%@.%@", imageName, @"png"]] options:NSAtomicWrite error:nil];
    } else if ([[extension lowercaseString] isEqualToString:@"jpg"] || [[extension lowercaseString] isEqualToString:@"jpeg"]) {
        [UIImageJPEGRepresentation(image, 1.0) writeToFile:[directoryPath stringByAppendingPathComponent:[NSString stringWithFormat:@"%@.%@", imageName, @"jpg"]] options:NSAtomicWrite error:nil];
    } else {
        NSLog(@"Image Save Failed\nExtension: (%@) is not recognized, use (PNG/JPG)", extension);
    }
}

-(UIImage *) loadImage:(NSString *)fileName ofType:(NSString *)extension inDirectory:(NSString *)directoryPath {
    UIImage * result = [UIImage imageWithContentsOfFile:[NSString stringWithFormat:@"%@/%@.%@", directoryPath, fileName, extension]];
    
    return result;
}


-(void) enregistrerPDF{

    NSString *resourceDocPath = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0];

    NSLog(@"Phase de téléchargement des PDF");
    for(NSString *tempPDF in self.listePDFDownload){
        
        NSString *filePath = [resourceDocPath
                              stringByAppendingPathComponent:[NSString stringWithFormat: @"%@.pdf",tempPDF]];
        
        if (![[NSFileManager defaultManager] fileExistsAtPath:filePath]) {
            
            //Construction de l'URL
            NSString *pathUrl = @"http://cap-horn.osmose-hebergement.com/ws/getCours?id=";
            pathUrl = [pathUrl stringByAppendingString:[NSString stringWithFormat: @"%@",tempPDF]];
            
            //download the file
            NSData *pdfData = [[NSData alloc] initWithContentsOfURL:[NSURL URLWithString:pathUrl]];
            [pdfData writeToFile:filePath atomically:YES];
        }
    }
}


-(void) enregistrementDerniereDateMAJ:(BOOL)modifDate{
    if(modifDate){
        NSDateFormatter *dateformate=[[NSDateFormatter alloc]init];
        [dateformate setDateFormat:@"ddMMYYYYHHmmss"];
        NSString *date_String=[dateformate stringFromDate:[NSDate date]];
        
        if(self.lastDateMaj==nil){
            self.lastDateMaj = [NSEntityDescription
                                insertNewObjectForEntityForName:@"Configuration"
                                inManagedObjectContext:self.managedObjectContext];
            self.lastDateMaj.id = [NSNumber numberWithInt:1];
            self.lastDateMaj.nom = @"Date de la dernière MAJ";
        }
            self.lastDateMaj.valeur = date_String;
    }
}


@end
