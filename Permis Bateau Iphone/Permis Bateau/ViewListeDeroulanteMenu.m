//
//  ViewListeDeroulanteSerie.m
//  Permis Bateau
//
//  Created by Alexandre Dubois on 16/11/2014.
//  Copyright (c) 2014 Alexandre Dubois. All rights reserved.
//

#import "ViewListeDeroulanteMenu.h"
#import "ViewCours.h"

@interface ViewListeDeroulanteMenu()

@end

@implementation ViewListeDeroulanteMenu




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
    // Remove table cell separator
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    
    // Assign our own backgroud for the view
    [self.tableView setBackgroundColor:[UIColor colorWithRed:213/255.0f green:230/255.0f blue:245/255.0f alpha:1.0f]];
    [self.tableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    
    // Add padding to the top of the table view
    UIEdgeInsets inset = UIEdgeInsetsMake(5, 0, 0, 0);
    self.tableView.contentInset = inset;
    
    self.boutonInformation = [UIButton buttonWithType:UIButtonTypeInfoDark];
}

- (void)viewDidUnload
{
    [super viewDidUnload];

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
    return 5;
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

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
     UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    
    
    if (!cell) {
         cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    }
    
    UILabel *recipeNameLabel = (UILabel *)[cell viewWithTag:101];
    UILabel *recipeDetailLabel = (UILabel *)[cell viewWithTag:102];
    UIImageView *recipeImageView = (UIImageView *)[cell viewWithTag:100];

    
    UIImage *background = [self cellBackgroundForRowAtIndexPath:indexPath];

    UIImageView *cellBackgroundView = [[UIImageView alloc] initWithImage:background];
    cell.backgroundColor = [UIColor clearColor];
    cellBackgroundView.image = background;
    cell.backgroundView = cellBackgroundView;
    
    
    if(indexPath.row == 0){
        recipeNameLabel.text = @"Examens thématiques";
        recipeDetailLabel.text = @"Des questions par thèmes";
        recipeImageView.image = [UIImage imageNamed:@"examensthematiques.png"];
    }else if(indexPath.row == 1){
        recipeNameLabel.text = @"Examens blancs";
        recipeDetailLabel.text = @"Conditions réelles d'examen";
        recipeImageView.image = [UIImage imageNamed:@"examensblancs.png"];
    }else if(indexPath.row == 2){
        recipeNameLabel.text = @"Passez votre permis";
        recipeDetailLabel.text = @"Effectuez la partie pratique";
        recipeImageView.image = [UIImage imageNamed:@"passerpermis.png"];
    }else if(indexPath.row == 3){
        recipeNameLabel.text = @"Historiques";
        recipeDetailLabel.text = @"Suivez votre évolution";
        recipeImageView.image = [UIImage imageNamed:@"historique.png"];
    }else if(indexPath.row == 4){
        recipeNameLabel.text = @"Cours";
        recipeDetailLabel.text = @"Révisez vos leçons";
        recipeImageView.image = [UIImage imageNamed:@"cours.png"];
    }
    
    return cell;
}


#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    switch (indexPath.row) {
        case 0: [self performSegueWithIdentifier:@"thematique" sender:self];
            break;
        case 1: [self performSegueWithIdentifier:@"blanc" sender:self];
            break;
        case 2: [self performSegueWithIdentifier:@"CoursDetails2" sender:self];
            break;
        case 4: [self performSegueWithIdentifier:@"cours" sender:self];
            break;
        case 3: [self performSegueWithIdentifier:@"historique" sender:self];
            break;
        default: break;
    }
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if([segue.identifier isEqualToString:@"CoursDetails"])
    {
        ViewCours *viewCours = (ViewCours *)[segue destinationViewController];
        viewCours.nomAppel = @"information";
    }else if ([segue.identifier isEqualToString:@"CoursDetails2"]){
        ViewCours *viewCours = (ViewCours *)[segue destinationViewController];
        viewCours.nomAppel = @"passerPermis";
    }
    

}

@end