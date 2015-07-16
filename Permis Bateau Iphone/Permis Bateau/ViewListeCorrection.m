//
//  ViewListeDeroulanteSerie.m
//  Permis Bateau
//
//  Created by Alexandre Dubois on 16/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import "ViewListeCorrection.h"
#import "ViewQuestionnaire.h"
#import "Question.h"
#import "Reponse.h"
#import "ViewListeDeroulanteMenu.h"
#import "ViewCorrectionDetail.h"

@interface ViewListeCorrection()
@property int nombreBonneReponse;

@end

@implementation ViewListeCorrection


- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    
    [self.navigationItem setHidesBackButton:YES animated:YES];
    //DESIGN
    // Add padding to the top of the table view
    UIEdgeInsets inset = UIEdgeInsetsMake(5, 0, 0, 0);
    self.tableView.contentInset = inset;
    [self.tableView setBackgroundColor:[UIColor colorWithRed:213/255.0f green:230/255.0f blue:245/255.0f alpha:1.0f]];
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    _nombreBonneReponse=0;
    
    [self coutNumberGoodAnswer];
    [self enregistrerTestPourStatistique];
}

-(void)declenchementPopUpWebService{
    
    if(!self.examenThematique){
    NSString *messageS;
    NSString *messageA;
    if(_nombreBonneReponse>24){
        messageA = @"Réussite";
        messageS = @"Vous avez commis moins de 5 fautes félicitation, continuez sur cette voie pour avoir votre permis côtier";
    }else{
        messageA = @"Echec";
        messageS = @"Vous avez commis plus de 5 fautes, vous n'avez pas votre permis côtier continuez vos efforts ne vous découragez pas";
    }
    
    UIAlertView *message = [[UIAlertView alloc] initWithTitle:messageA
                                                      message:messageS
                                                     delegate:self
                                            cancelButtonTitle:@"Ok"
                                            otherButtonTitles:nil, nil, nil];
    [message show];
    }

    [self.boutonMenu setBackgroundColor:[UIColor whiteColor]];
    self.boutonMenu.layer.cornerRadius = 8;
    

    
}


- (void)coutNumberGoodAnswer{
    int idx = 0;
    for (Question *questionTemp in self.questionnaire.listeQuestion)
    {
        if(questionTemp != nil){
            Reponse *reponse = [self.questionnaire.listeReponse objectAtIndex:idx];
            
            BOOL reponseCorrect = YES;
            
            if(questionTemp.reponseA!=nil){
                if(([questionTemp.reponseCorrectA boolValue] && !reponse.reponseA) || (![questionTemp.reponseCorrectA boolValue] && reponse.reponseA)){
                reponseCorrect = NO;
                }
            }
            if(questionTemp.reponseB!=nil){
                if(([questionTemp.reponseCorrectB boolValue] && !reponse.reponseB) || (![questionTemp.reponseCorrectB boolValue] && reponse.reponseB)){
                reponseCorrect = NO;
            }
            }
            if(questionTemp.reponseC!=nil){
                if(([questionTemp.reponseCorrectC boolValue] && !reponse.reponseC) || (![questionTemp.reponseCorrectC boolValue] && reponse.reponseC)){
                reponseCorrect = NO;
                }
            }
            if(questionTemp.reponseD!=nil){
                if(([questionTemp.reponseCorrectD boolValue] && !reponse.reponseD) || (![questionTemp.reponseCorrectD boolValue] && reponse.reponseD)){
                reponseCorrect = NO;
                }
            }
            
            if(reponseCorrect){
                _nombreBonneReponse++;
            }
        }
        idx ++;
    }
}



- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex{
}



- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (UIImage *)cellBackgroundForRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSInteger rowCount = [self tableView:[self tableView] numberOfRowsInSection:0];
    NSInteger rowIndex = indexPath.row;
    UIImage *background = nil;
    
    if (rowIndex == 0) {
        background = [UIImage imageNamed:@"cell_top.png"];
    } else if (rowIndex == rowCount - 1) {
        background = [UIImage imageNamed:@"cell_bottom.png"];
    } else {
        background = [UIImage imageNamed:@"cell_middle.png"];
    }
    
    return background;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [self.questionnaire.listeQuestion count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
    }
    
    // Configure the cell...
    Question *questionTemp = [self.questionnaire.listeQuestion objectAtIndex:indexPath.row];
    Reponse *reponse = [self.questionnaire.listeReponse objectAtIndex:indexPath.row];
    
    
    UIImageView *recipeImageView = (UIImageView *)[cell viewWithTag:100];
    UILabel *recipeNameLabel = (UILabel *)[cell viewWithTag:101];
    
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
        recipeImageView.image = [UIImage imageNamed:@"correction_cancel.png"];
    }else{
        recipeImageView.image = [UIImage imageNamed:@"tucomprendcasionsion.png"];
    }
    
    UIImage *background = [self cellBackgroundForRowAtIndexPath:indexPath];
    
    UIImageView *cellBackgroundView = [[UIImageView alloc] initWithImage:background];
    cell.backgroundColor = [UIColor clearColor];
    cellBackgroundView.image = background;
    cell.backgroundView = cellBackgroundView;
    
    recipeNameLabel.text = [NSString stringWithFormat:@"Question n°%@", [questionTemp.numero stringValue]];
    
    

    
    return cell;
}

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Navigation logic may go here. Create and push another view controller.
    /*
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
     */
}



- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    
    if (![sender isMemberOfClass:[UIButton class]])
    {
        Question *question;
        Reponse *reponse;
        
        NSIndexPath *indexPath = [self.tableView indexPathForSelectedRow];
        
        question = [self.questionnaire.listeQuestion objectAtIndex:indexPath.item];
        reponse = [self.questionnaire.listeReponse objectAtIndex:indexPath.item];
        
        ViewCorrectionDetail *viewCorrectionDetail = (ViewCorrectionDetail *)[segue destinationViewController];
        viewCorrectionDetail.question = question;
        viewCorrectionDetail.reponse = reponse;
    }
}



- (void)enregistrerTestPourStatistique{
    
    
        [self declenchementPopUpWebService];
    
    
    id delegate = [[UIApplication sharedApplication] delegate];
    self.managedObjectContext = [delegate managedObjectContext];
    
    
    
    
    Historique *historique = [NSEntityDescription
                              insertNewObjectForEntityForName:@"Historique"
                              inManagedObjectContext:self.managedObjectContext];
    
    
    historique.serie = self.serieEnCours;
    
    if(_nombreBonneReponse>24){
        historique.blancReussit = [NSNumber numberWithBool:YES];
    }else{
        historique.blancReussit = [NSNumber numberWithBool:NO];
    }
    

    
    NSString *constructionDescription = @"";
    constructionDescription = [NSString stringWithFormat: @"%d/%ld", _nombreBonneReponse, (unsigned long)self.questionnaire.listeQuestion.count];

    self.navigationBarCorrection.title = constructionDescription;
    
    historique.resultat = constructionDescription;
    
    NSDate *currDate = [NSDate date];
    
    historique.date = currDate;
    
    
    NSError *error;
    if (![self.managedObjectContext save:&error]) {
        NSLog(@"La sauvegarde n'a pas reussi pour la table Historique: %@", [error localizedDescription]);
    }
}


@end